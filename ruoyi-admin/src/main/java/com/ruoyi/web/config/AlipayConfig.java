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
public class AlipayConfig {
    // 编码格式常量
    private static final String CHARSET = "UTF-8";
    // 签名算法常量
    private static final String SIGN_TYPE = "RSA2";

    private static final Logger log = LoggerFactory.getLogger(AlipayConfig.class);
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

    /**
     * 初始化支付宝客户端
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


    // 回调验证工具方法（4.40.0版本推荐方式）
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

    // getter方法
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }
}
