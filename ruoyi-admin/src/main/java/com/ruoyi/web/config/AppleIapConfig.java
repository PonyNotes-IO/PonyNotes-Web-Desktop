package com.ruoyi.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Apple 应用内购买（IAP / StoreKit 2）连接配置
 *
 * 配置位置 application.yml: payment.apple.*
 * 商品与套餐的映射规则（无需数据库配置）：
 *   苹果商品ID = productPrefix + 计费周期(monthly/yearly) + "." + 套餐编码(plan_code)
 *   如 com.ponynotes.monthly.standard
 */
@Component
@ConfigurationProperties(prefix = "payment.apple")
public class AppleIapConfig {

    /** 是否启用苹果内购 */
    private boolean enabled = false;

    /** App 的 Bundle ID（验签时校验 transaction.bundleId 必须一致） */
    private String bundleId;

    /** SANDBOX 或 PRODUCTION */
    private String environment = "SANDBOX";

    /** 苹果商品ID固定前缀（商品ID = 前缀 + monthly/yearly + 套餐编码） */
    private String productPrefix = "ponynotes.";

    /** App Store Connect 集成密钥：Issuer ID */
    private String issuerId;

    /** App Store Connect 集成密钥：Key ID */
    private String keyId;

    /** .p8 私钥文件路径（classpath 相对路径，放在 resources/cert 下） */
    private String privateKeyPath;

    /** App Store 服务器通知 URL（配置到 App Store Connect，本服务提供 /api/payment/callback/apple） */
    private String notifyUrl;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getProductPrefix() {
        return productPrefix;
    }

    public void setProductPrefix(String productPrefix) {
        this.productPrefix = productPrefix;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
