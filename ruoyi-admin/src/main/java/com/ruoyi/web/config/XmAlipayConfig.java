package com.ruoyi.web.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class XmAlipayConfig {
    // 编码格式常量
    private static final String CHARSET = "UTF-8";
    // 签名算法常量
    private static final String SIGN_TYPE = "RSA2";

    private static final Logger log = LoggerFactory.getLogger(XmAlipayConfig.class);

    // ==================== 网页支付配置（appid A，已上线，请勿改动）====================
    @Value("${payment.alipay.app-id}")
    private String appId;

    @Value("${payment.alipay.private-key}")
    private String privateKey;

    @Value("${payment.alipay.public-key}")
    private String publicKey;

    @Value("${payment.alipay.gateway-url}")
    private String gatewayUrl;

    @Value("${payment.alipay.notify-url}")
    private String notifyUrl;

    // 新增超时时间配置（单位：毫秒）
    @Value("${payment.alipay.connect-timeout:30000}")
    private int connectTimeout;

    @Value("${payment.alipay.read-timeout:30000}")
    private int readTimeout;

    @Value("${payment.alipay.return-url}")
    private String returnUrl;

    // ==================== App 支付配置（appid B，独立应用/独立密钥）====================
    @Value("${payment.alipay.app.app-id:}")
    private String appAppId;

    @Value("${payment.alipay.app.private-key:}")
    private String appPrivateKey;

    @Value("${payment.alipay.app.public-key:}")
    private String appPublicKey;

    /**
     * 初始化支付宝客户端（网页支付，appid A）
     * Bean 名称：alipayClient（保持不变，网页支付逻辑完全不受影响）
     */
    @Bean
    public AlipayClient alipayClient() {
        DefaultAlipayClient client = new DefaultAlipayClient(
                gatewayUrl,
                appId,
                privateKey,
                "json",
                CHARSET,
                publicKey,
                "RSA2"
        );
        // 设置超时时间
        client.setConnectTimeout(connectTimeout);
        client.setReadTimeout(readTimeout);
        return client;
    }

    /**
     * 初始化支付宝客户端（App 支付，appid B）
     * Bean 名称：appAlipayClient
     * 说明：App 支付下单调用 sdkExecute（本地签名），网关仅退款/查询时用到。
     * 若未配置 payment.alipay.app.app-id，则复用网页支付的 alipayClient（兼容单 appid 场景）。
     */
    @Bean("appAlipayClient")
    public AlipayClient appAlipayClient() {
        // 未配置 App 支付独立 appid 时，回退到网页支付客户端，避免启动失败
        if (appAppId == null || appAppId.isEmpty()) {
            log.warn("未配置 payment.alipay.app.app-id，appAlipayClient 回退复用网页支付 alipayClient");
            return alipayClient();
        }
        return new DefaultAlipayClient(
                gatewayUrl,
                appAppId,
                appPrivateKey,
                "json",
                CHARSET,
                appPublicKey,
                "RSA2"
        );
    }

    // ==================== 验签相关 ====================

    /**
     * 回调验证（网页支付，app_id A 的公钥）
     */
    public boolean verifySign(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(
                  params,
                    publicKey,
                    CHARSET,
                    SIGN_TYPE
            );
        } catch (Exception e) {
            log.error("支付宝签名验证失败", e);
            return false;
        }
    }

    /**
     * 回调验证（App 支付，app_id B 的公钥）
     */
    public boolean verifySignByApp(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(
                    params,
                    appPublicKey,
                    CHARSET,
                    SIGN_TYPE
            );
        } catch (Exception e) {
            log.error("支付宝(App)签名验证失败", e);
            return false;
        }
    }

    /**
     * 根据回调参数中的 app_id 自动选择对应公钥验签（双 appid 场景统一入口）。
     * - 回调 app_id 等于网页支付 appId → 用公钥 A
     * - 回调 app_id 等于 App 支付 appAppId → 用公钥 B
     * - 未配置 appAppId 时统一用公钥 A（兼容单 appid 场景）
     */
    public boolean verifySignByAppId(Map<String, String> params) {
        String callbackAppId = params != null ? params.get("app_id") : null;
        if (callbackAppId != null && callbackAppId.equals(appAppId)) {
            return verifySignByApp(params);
        }
        // 默认走网页支付公钥
        return verifySign(params);
    }

    // getter方法
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppAppId() {
        return appAppId;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
