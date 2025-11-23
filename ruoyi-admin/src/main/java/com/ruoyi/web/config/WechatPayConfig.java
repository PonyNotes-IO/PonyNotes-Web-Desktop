package com.ruoyi.web.config;

// Java 标准库
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
// 微信支付 SDK 相关
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
//import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Spring 框架相关
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@Component
@Configuration
public class WechatPayConfig {

    Logger logger = LoggerFactory.getLogger(WechatPayConfig.class);

    // 新增：注入app-id和app-secret
    @Value("${payment.wechat.app-id}")
    private String appId; // 原代码中getAppId()返回了mchId，需修正

    @Value("${payment.wechat.app-secret}")
    private String appSecret; // 新增appsecret字段

    @Value("${payment.wechat.mch-id}")
    private String mchId;

    @Value("${payment.wechat.mch-serial-no}")
    private String mchSerialNo;

    @Value("${payment.wechat.private-key-path}")
    private String privateKeyPath;

    @Value("${payment.wechat.public-key-path}")
    private String publicKeyPath;
    @Value("${payment.wechat.api-v3-key}")
    private String apiV3Key;

    @Value("${payment.wechat.notify-url}")
    private String notifyUrl;
    @Value("${payment.wechat.pub_key_id}")
    private String publicKeyId;

    /**
     * 初始化微信支付配置（自动加载证书）
     */
    @Bean
    public Config WechatPayConfig() {
        try {
            checkTimeSync(); // 启用时间检查
            Resource resource = new ClassPathResource(privateKeyPath);
            Resource pubRec = new ClassPathResource(publicKeyPath);
            if (!resource.exists()) {
                throw new FileNotFoundException("私钥文件不存在：" + privateKeyPath);
            }
            if (!pubRec.exists()) {
                throw new FileNotFoundException("公钥文件不存在：" + publicKeyPath);
            }
            // 读取私钥内容
            String privateKeyPEM;
            try (InputStream in = resource.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                privateKeyPEM = baos.toString(StandardCharsets.UTF_8.name());
            }
            // 读取私钥内容
            String pucKeyPEM;
            try (InputStream in = pubRec.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                pucKeyPEM = baos.toString(StandardCharsets.UTF_8.name());
            }
            Config config = new RSAPublicKeyConfig.Builder()
                    .merchantId(mchId)
//                    .privateKeyFromPath(privateKeyPath)
                    .privateKey(privateKeyPEM)
//                    .publicKeyFromPath(publicKeyPath)
                    .publicKey(pucKeyPEM)
                    .publicKeyId(publicKeyId)
                    .merchantSerialNumber(mchSerialNo)
                    .apiV3Key(apiV3Key)
                    .build();
            // 构建配置（自动管理证书，无需手动下载根证书）
            // return new RSAAutoCertificateConfig.Builder()
            // .merchantId(mchId)
            // .privateKey(privateKeyPEM)
            // .merchantSerialNumber(mchSerialNo)
            // .apiV3Key(apiV3Key)
            // .build();
            return config;
        } catch (Exception e) {
            logger.error("微信支付配置异常", e);
        }
        return null;
    }

    // /**
    // * 初始化微信支付配置（自动加载证书）
    // */
    // @Bean
    // public RSAAutoCertificateConfig rsaAutoCertificateConfig() {
    // try {
    // // 1. 检查时间同步状态
    // checkTimeSync();
    //
    // // 2. 加载私钥文件
    // Resource resource = new ClassPathResource(privateKeyPath);
    // if (!resource.exists()) {
    // throw new FileNotFoundException("私钥文件不存在：" + privateKeyPath);
    // }
    //
    // // 3. 读取私钥内容
    // String privateKeyPEM;
    // try (InputStream in = resource.getInputStream();
    // ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
    // byte[] buffer = new byte[8192];
    // int len;
    // while ((len = in.read(buffer)) != -1) {
    // baos.write(buffer, 0, len);
    // }
    // privateKeyPEM = baos.toString(StandardCharsets.UTF_8.name());
    // }
    //
    // // 4. 构建配置（自动管理证书，无需手动下载根证书）
    // return new RSAAutoCertificateConfig.Builder()
    // .merchantId(mchId)
    // .privateKey(privateKeyPEM)
    // .merchantSerialNumber(mchSerialNo)
    // .apiV3Key(apiV3Key)
    // .build();
    //
    // } catch (Exception e) {
    // logger.error("微信支付配置异常", e);
    // throw new RuntimeException("微信支付配置初始化失败", e);
    // }
    // }

    /**
     * 检查时间同步状态
     */
    private void checkTimeSync() {
        try {
            // 获取当前系统时间
            long currentTime = System.currentTimeMillis();
            logger.info("当前系统时间: {}", new Date(currentTime));

            // 简单校验时间是否合理（不早于2020年）
            if (currentTime < 1577836800000L) { // 2020-01-01
                throw new RuntimeException("系统时间异常，请检查时间同步设置");
            }
        } catch (Exception e) {
            logger.error("时间同步检查失败", e);
            throw new RuntimeException("时间同步检查失败，请确保服务器时间已同步", e);
        }
    }

    // /**
    // * 微信Native支付服务（生成二维码）
    // */
    // @Bean
    // public NativePayService nativePayService(Config config) {
    // try {
    // return new NativePayService.Builder().config(config).build();
    //
    // } catch (Exception e) {
    // logger.error("支付服务初始化异常",e);
    // return null;
    // }
    // }
    // public String getNotifyUrl() {
    // return notifyUrl;
    // }
    @Bean
    public com.wechat.pay.java.service.partnerpayments.nativepay.NativePayService nativePayService() {
        try {
            // 使用当前配置类创建的Config对象构建支付服务
            return new com.wechat.pay.java.service.partnerpayments.nativepay.NativePayService.Builder()
                    .config(WechatPayConfig()) // 引用当前类的WechatPayConfig()方法返回的配置
                    .build();
        } catch (Exception e) {
            logger.error("微信Native支付服务初始化失败", e);
            throw new RuntimeException("微信支付服务初始化异常", e);
        }
    }

    @Bean
    public JsapiService jsapiPayService() {
        try {
            return new JsapiService.Builder()
                    .config(WechatPayConfig()) // 复用现有配置
                    .build();
        } catch (Exception e) {
            logger.error("微信JSAPI支付服务初始化失败", e);
            throw new RuntimeException("JSAPI支付服务初始化异常", e);
        }
    }
    /**
     * 验证微信支付回调通知签名
     * 
     * @param params 包含 body(通知数据), timestamp(通知时间戳), nonce(随机串), signature(签名),
     *               serial(证书序列号)
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
                SecretKeySpec secretKeySpec = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8),
                        "HmacSHA256");
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

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getPublicKeyId() {
        return publicKeyId;
    }

    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }
}
