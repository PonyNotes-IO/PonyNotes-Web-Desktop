package com.ruoyi.web.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatPayConfiguration {

    Logger logger = LoggerFactory.getLogger(WechatPayConfiguration.class);

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
}
