package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 用户补充包（用户已购买的补充包）
 */
public class UserAddon implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userAddonId;
    private String userId;
    private Long addonId;
    private String addonType; // 补充包类型：storage/ai_quota/priority
    private String addonName; // 补充包名称
    private Integer quantity; // 购买数量
    private Integer quantityUsed; // 已使用数量
    private String status; // 状态：active/expired/used
    private Long purchaseDate; // 购买日期（时间戳）
    private Long expireDate; // 过期日期（时间戳）
    private Long createTime;
    private Long updateTime;

    public UserAddon() {
    }

    // Getters and Setters
    public Long getUserAddonId() {
        return userAddonId;
    }

    public void setUserAddonId(Long userAddonId) {
        this.userAddonId = userAddonId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
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
        return "UserAddon{" +
                "userAddonId=" + userAddonId +
                ", userId='" + userId + '\'' +
                ", addonName='" + addonName + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }
}
