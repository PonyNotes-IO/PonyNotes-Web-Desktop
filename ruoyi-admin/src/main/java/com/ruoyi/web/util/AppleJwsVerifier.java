package com.ruoyi.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Apple JWS（JSON Web Signature）验签工具。
 *
 * Apple 的 App Store Server API / Notifications V2 / StoreKit 2 交易数据都是
 * ES256 签名的 JWS（header.payload.signature），header.x5c 携带证书链：
 * [签名证书, 中间证书(WWDR), Apple Root CA]。
 *
 * 本工具不依赖第三方库（项目为 Java 8，Apple 官方库要求 Java 11+），
 * 用 JDK 自带 JCA 完成：
 *   1. 验证 payload 签名（叶子证书公钥，SHA256withECDSA，需把 JWS 的 r||s 原始签名转 DER）
 *   2. 验证证书链（逐级验签 + 顶层证书必须是本地信任的 Apple Root CA）
 *   3. 校验叶子证书有效期
 */
public class AppleJwsVerifier {

    private static final Logger log = LoggerFactory.getLogger(AppleJwsVerifier.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** 本地信任的 Apple 根证书（DER 格式，classpath 下） */
    private static final String[] TRUSTED_ROOT_CERTS = {
            "cert/AppleRootCA-G3.cer",
            "cert/AppleRootCA-G4.cer"
    };

    private final List<X509Certificate> trustedRoots = new ArrayList<>();

    private static volatile AppleJwsVerifier instance;

    private AppleJwsVerifier() {
        CertificateFactory factory;
        try {
            factory = CertificateFactory.getInstance("X.509");
            for (String path : TRUSTED_ROOT_CERTS) {
                try (InputStream in = new ClassPathResource(path).getInputStream()) {
                    trustedRoots.add((X509Certificate) factory.generateCertificate(in));
                } catch (Exception e) {
                    log.warn("加载 Apple 根证书失败：{}", path, e);
                }
            }
        } catch (Exception e) {
            log.error("初始化证书工厂失败", e);
        }
        if (trustedRoots.isEmpty()) {
            throw new IllegalStateException("未加载到任何 Apple 根证书，无法验签");
        }
    }

    public static AppleJwsVerifier getInstance() {
        if (instance == null) {
            synchronized (AppleJwsVerifier.class) {
                if (instance == null) {
                    instance = new AppleJwsVerifier();
                }
            }
        }
        return instance;
    }

    /**
     * 验签并解析 JWS，返回 payload 的 JSON。
     *
     * @param jws 形如 header.payload.signature 的紧凑序列化字符串
     * @throws IllegalArgumentException 验签失败 / 格式非法
     */
    public JsonNode verifyAndDecode(String jws) {
        if (jws == null || jws.isEmpty()) {
            throw new IllegalArgumentException("JWS 为空");
        }
        String[] parts = jws.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("JWS 格式非法");
        }

        try {
            Base64.Decoder urlDecoder = Base64.getUrlDecoder();
            JsonNode header = MAPPER.readTree(urlDecoder.decode(parts[0]));
            byte[] payloadBytes = urlDecoder.decode(parts[1]);
            byte[] signature = urlDecoder.decode(parts[2]);

            if (!"ES256".equals(header.get("alg").asText())) {
                throw new IllegalArgumentException("不支持的签名算法：" + header.get("alg").asText());
            }
            JsonNode x5c = header.get("x5c");
            if (x5c == null || !x5c.isArray() || x5c.size() < 2) {
                throw new IllegalArgumentException("JWS header 缺少 x5c 证书链");
            }

            // 1. 解析证书链
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            List<X509Certificate> chain = new ArrayList<>();
            for (JsonNode node : x5c) {
                byte[] certBytes = Base64.getDecoder().decode(node.asText());
                chain.add((X509Certificate) factory.generateCertificate(
                        new java.io.ByteArrayInputStream(certBytes)));
            }

            // 2. 叶子证书有效期校验
            chain.get(0).checkValidity();

            // 3. 证书链逐级验签（cert[i] 由 cert[i+1] 签发）
            for (int i = 0; i < chain.size() - 1; i++) {
                verifyCertificateSignature(chain.get(i), chain.get(i + 1));
            }

            // 4. 链顶端（可能是链里自带的根证书，也可能是中间证书）必须可追溯到本地信任的 Apple 根
            X509Certificate top = chain.get(chain.size() - 1);
            boolean trusted = false;
            for (X509Certificate root : trustedRoots) {
                if (top.equals(root)
                        || (top.getIssuerX500Principal().equals(root.getSubjectX500Principal())
                            && verifySig(top, root.getPublicKey()))) {
                    trusted = true;
                    break;
                }
            }
            if (!trusted) {
                throw new IllegalArgumentException("证书链不能追溯到受信任的 Apple 根证书");
            }

            // 5. 用叶子证书公钥验证 JWS 签名（JWS ES256 签名为 raw r||s，需转 DER）
            byte[] signingInput = (parts[0] + "." + parts[1]).getBytes(java.nio.charset.StandardCharsets.US_ASCII);
            PublicKey leafKey = chain.get(0).getPublicKey();
            Signature verifier = Signature.getInstance("SHA256withECDSA");
            verifier.initVerify(leafKey);
            verifier.update(signingInput);
            if (!verifier.verify(toDerSignature(signature))) {
                throw new IllegalArgumentException("JWS 签名验证失败");
            }

            return MAPPER.readTree(payloadBytes);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Apple JWS 验签异常", e);
            throw new IllegalArgumentException("Apple JWS 验签失败：" + e.getMessage());
        }
    }

    /** 校验 cert 是否由 issuer 公钥签名 */
    private void verifyCertificateSignature(X509Certificate cert, X509Certificate issuer) throws Exception {
        if (!verifySig(cert, issuer.getPublicKey())) {
            throw new IllegalArgumentException("证书链验签失败：" + cert.getSubjectX500Principal());
        }
    }

    private boolean verifySig(X509Certificate cert, PublicKey issuerKey) throws Exception {
        try {
            cert.verify(issuerKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** JWS 的 ES256 签名是 64 字节 raw r||s，JCA 需要 ASN.1 DER 序列化格式 */
    private static byte[] toDerSignature(byte[] raw) {
        if (raw.length != 64) {
            throw new IllegalArgumentException("ES256 签名长度非法：" + raw.length);
        }
        byte[] r = trimLeadingZeros(java.util.Arrays.copyOfRange(raw, 0, 32));
        byte[] s = trimLeadingZeros(java.util.Arrays.copyOfRange(raw, 32, 64));

        int totalLen = 2 + r.length + 2 + s.length;
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        out.write(0x30); // SEQUENCE
        out.write(totalLen > 127 ? 0x81 : totalLen);
        if (totalLen > 127) {
            out.write(totalLen);
        }
        out.write(0x02); // INTEGER r
        out.write(r.length);
        out.write(r, 0, r.length);
        out.write(0x02); // INTEGER s
        out.write(s.length);
        out.write(s, 0, s.length);
        return out.toByteArray();
    }

    private static byte[] trimLeadingZeros(byte[] in) {
        int i = 0;
        while (i < in.length - 1 && in[i] == 0) {
            i++;
        }
        // 若最高位为 1，DER 正数需补 0x00 前缀
        boolean needPad = (in[i] & 0x80) != 0;
        byte[] result = new byte[in.length - i + (needPad ? 1 : 0)];
        System.arraycopy(in, i, result, needPad ? 1 : 0, in.length - i);
        return result;
    }
}
