package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 补充包模型（例如：额外存储空间、AI额度等）
 */
public class SubscriptionAddon implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long addonId;
    private String addonType; // 补充包类型：storage/ai_quota/priority
    private String addonName; // 补充包名称
    private String addonDisplayName; // 显示名称
    private String description; // 描述
    private BigDecimal price; // 价格
    private Integer quantity; // 数量（例如：10GB存储、100次AI调用等）
    private String quantityUnit; // 单位（GB、次等）
    private Boolean isActive; // 是否可用
    private Long createTime;
    private Long updateTime;

    public SubscriptionAddon() {
    }

    public SubscriptionAddon(Long addonId, String addonType, String addonName, String addonDisplayName,
            String description, BigDecimal price, Integer quantity, String quantityUnit) {
        this.addonId = addonId;
        this.addonType = addonType;
        this.addonName = addonName;
        this.addonDisplayName = addonDisplayName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.isActive = true;
    }

    // Getters and Setters
    public Long getAddonId() {
        return addonId;
    }

    public void setAddonId(Long addonId) {
        this.addonId = addonId;
    }

    public String getAddonType() {
        return addonType;
    }

    public void setAddonType(String addonType) {
        this.addonType = addonType;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public String getAddonDisplayName() {
        return addonDisplayName;
    }

    public void setAddonDisplayName(String addonDisplayName) {
        this.addonDisplayName = addonDisplayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SubscriptionAddon{" +
                "addonId=" + addonId +
                ", addonType='" + addonType + '\'' +
                ", addonName='" + addonName + '\'' +
                ", addonDisplayName='" + addonDisplayName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", quantityUnit='" + quantityUnit + '\'' +
                '}';
    }
}
