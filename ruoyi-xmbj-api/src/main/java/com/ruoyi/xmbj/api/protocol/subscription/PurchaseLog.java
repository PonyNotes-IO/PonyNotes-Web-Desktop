package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 购买日志
 */
public class PurchaseLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long logId;
    private String userId;
    private Long addonId;
    private String addonName;
    private Integer quantity;
    private String description;
    private Long createTime;

    public PurchaseLog() {
    }

    // Getters and Setters
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAddonId() {
        return addonId;
    }

    public void setAddonId(Long addonId) {
        this.addonId = addonId;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
                "logId=" + logId +
                ", addonName='" + addonName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
