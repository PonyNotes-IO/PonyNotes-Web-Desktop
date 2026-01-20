package com.ruoyi.web.config;
// Java 标准库
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
// 微信支付 SDK 相关
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Spring 框架相关
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Map;

@Configuration
public class WechatPayConfig {

    Logger logger = LoggerFactory.getLogger(WechatPayConfig.class);

    // 新增：注入app-id和app-secret
    @Value("${payment.wechat.app-id}")
    private String appId;  // 原代码中getAppId()返回了mchId，需修正

    @Value("${payment.wechat.app-secret}")
    private String appSecret;  // 新增appsecret字段

    @Value("${payment.wechat.mch-id}")
    private String mchId;

    @Value("${payment.wechat.mch-serial-no}")
    private String mchSerialNo;

    @Value("${payment.wechat.private-key-path}")
    private String privateKeyPath;

    @Value("${payment.wechat.api-v3-key}")
    private String apiV3Key;

    @Value("${payment.wechat.notify-url}")
    private String notifyUrl;

    /**
     * 初始化微信支付配置（自动加载证书）
     */
//    @Bean
//    public Config WechatPayConfig() {
//        try {
//            Resource resource = new ClassPathResource(privateKeyPath);
//            if (!resource.exists()) {
//                throw new FileNotFoundException("私钥文件不存在：" + privateKeyPath);
//            }
//            // 读取私钥内容
//            String privateKeyPEM;
//            try (InputStream in = resource.getInputStream();
//                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//                byte[] buffer = new byte[8192];
//                int len;
//                while ((len = in.read(buffer)) != -1) {
//                    baos.write(buffer, 0, len);
//                }
//                privateKeyPEM = baos.toString(StandardCharsets.UTF_8.name());
//            }
//            // 构建配置（自动管理证书，无需手动下载根证书）
//            return new RSAAutoCertificateConfig.Builder()
//                    .merchantId(mchId)
//                    .privateKey(privateKeyPEM)
//                    .merchantSerialNumber(mchSerialNo)
//                    .apiV3Key(apiV3Key)
//                    .build();
//        } catch (Exception e) {
//            logger.error("微信支付配置异常",e);
//        }
//        return null;
//    }

//    /**
//     * 微信Native支付服务（生成二维码）
//     */
//    @Bean
//    public NativePayService nativePayService(Config config) {
//        try {
//            return new NativePayService.Builder().config(config).build();
//
//        } catch (Exception e) {
//            logger.error("支付服务初始化异常",e);
//            return null;
//        }
//    }
//    public String getNotifyUrl() {
//        return notifyUrl;
//    }

    /**
     * 验证微信支付回调通知签名
     * @param params 包含 body(通知数据), timestamp(通知时间戳), nonce(随机串), signature(签名), serial(证书序列号)
     * @return 验签结果
     */
    public boolean verifySign(Map<String, String> params) {
        try {
            String body = params.get("body");
            String timestamp = params.get("timestamp");
            String nonce = params.get("nonce");
            String signature = params.get("signature");
            String apiV3Key = params.get("apiV3Key");

            if (body == null || timestamp == null || nonce == null || signature == null || apiV3Key == null) {
                logger.error("微信支付回调参数不完整");
                return false;
            }

            // 构造验签名串
            String message = timestamp + "\n" + nonce + "\n" + body + "\n";

            try {
                // 初始化 HMAC-SHA256
                Mac mac = Mac.getInstance("HmacSHA256");
                SecretKeySpec secretKeySpec = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
                mac.init(secretKeySpec);

                // 计算签名
                byte[] hash = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

                // 转换为十六进制字符串
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                // 比较签名是否一致
                return hexString.toString().equals(signature);

            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                logger.error("微信支付回调验签失败: {}", e.getMessage());
                return false;
            }

        } catch (Exception e) {
            logger.error("微信支付回调验签异常: {}", e.getMessage());
            return false;
        }
    }
    public String getAppId() {
        return appId;
    }

    public String getMchId() {
        return mchId;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchSerialNo() {
        return mchSerialNo;
    }

    public void setMchSerialNo(String mchSerialNo) {
        this.mchSerialNo = mchSerialNo;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}

