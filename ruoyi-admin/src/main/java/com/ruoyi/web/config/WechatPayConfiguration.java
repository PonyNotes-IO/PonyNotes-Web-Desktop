package com.ruoyi.web.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Map;

@Configuration
public class WechatPayConfiguration {

    Logger logger = LoggerFactory.getLogger(WechatPayConfiguration.class);

    Logger logger = LoggerFactory.getLogger(WechatPayConfig.class);

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
    @Bean
    public Config wechatPayConfig() {
        try{
            return new RSAAutoCertificateConfig.Builder()
                    .merchantId(mchId)
                    .privateKeyFromPath(privateKeyPath)
                    .merchantSerialNumber(mchSerialNo)
                    .apiV3Key(apiV3Key)
                    .build();
        } catch (Exception e) {
            logger.error("微信支付配置异常",e);
        }
        return null;
    }

    /**
     * 微信Native支付服务（生成二维码）
     */
    @Bean
    public NativePayService nativePayService(Config config) {
        try {
            return new NativePayService.Builder().config(config).build();

        } catch (Exception e) {
            logger.error("支付服务初始化异常",e);
            return null;
        }
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }

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
        return mchId;
    }

    public String getMchId() {
        return mchId;
    }
}
