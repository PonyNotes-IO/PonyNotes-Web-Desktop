package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户当前订阅信息
 */
public class UserSubscription implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long subscriptionId;
    private String userId;
    private Long planId;
    private String planName; // 订阅的计划名称
    private String billingType; // 计费类型：monthly/annual
    private Long startDate; // 开始日期（时间戳）
    private Long endDate; // 结束日期（时间戳）
    private String status; // 状态：active/inactive/cancelled
    private BigDecimal amount; // 订阅金额
    private Integer storageQuota; // 存储空间配额(GB)
    private Integer aiCallQuota; // AI调用额度配额(次)
    private Integer storageUsed; // 已使用存储(GB)
    private Integer aiCallUsed; // 已使用AI调用(次)
    private Boolean autoRenew; // 是否自动续费
    private Long createTime;
    private Long updateTime;

    public UserSubscription() {
    }

    // Getters and Setters
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStorageQuota() {
        return storageQuota;
    }

    public void setStorageQuota(Integer storageQuota) {
        this.storageQuota = storageQuota;
    }

    public Integer getAiCallQuota() {
        return aiCallQuota;
    }

    public void setAiCallQuota(Integer aiCallQuota) {
        this.aiCallQuota = aiCallQuota;
    }

    public Integer getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(Integer storageUsed) {
        this.storageUsed = storageUsed;
    }

    public Integer getAiCallUsed() {
        return aiCallUsed;
    }

    public void setAiCallUsed(Integer aiCallUsed) {
        this.aiCallUsed = aiCallUsed;
    }

    public Boolean getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
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
        return "UserSubscription{" +
                "subscriptionId=" + subscriptionId +
                ", userId='" + userId + '\'' +
                ", planName='" + planName + '\'' +
                ", status='" + status + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}
