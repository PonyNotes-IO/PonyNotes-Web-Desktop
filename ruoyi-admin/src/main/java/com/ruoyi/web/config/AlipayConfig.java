package com.ruoyi.web.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

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

    /**
     * 初始化支付宝客户端
     */
    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                gatewayUrl,
                appId,
                privateKey,
                "json",
                "UTF-8",
                publicKey,
                "RSA2"  // 签名算法
        );
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}
