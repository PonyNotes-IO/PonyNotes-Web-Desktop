package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订阅计划模型
 */
public class SubscriptionPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long planId;
    private String planName; // 计划名称：free/student/standard/team
    private String planDisplayName; // 显示名称：免费版/学生版/标准版/团队版
    private String description; // 计划描述
    private BigDecimal monthlyPrice; // 月付价格
    private BigDecimal annualPrice; // 年付价格
    private Integer storageSpace; // 存储空间(GB)
    private Integer aiQuota; // AI调用额度(次)
    private Boolean isActive; // 是否可用
    private Long createTime;
    private Long updateTime;

    public SubscriptionPlan() {
    }

    public SubscriptionPlan(Long planId, String planName, String planDisplayName, String description,
            BigDecimal monthlyPrice, BigDecimal annualPrice, Integer storageSpace, Integer aiQuota) {
        this.planId = planId;
        this.planName = planName;
        this.planDisplayName = planDisplayName;
        this.description = description;
        this.monthlyPrice = monthlyPrice;
        this.annualPrice = annualPrice;
        this.storageSpace = storageSpace;
        this.aiQuota = aiQuota;
        this.isActive = true;
    }

    // Getters and Setters
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

    public String getPlanDisplayName() {
        return planDisplayName;
    }

    public void setPlanDisplayName(String planDisplayName) {
        this.planDisplayName = planDisplayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public BigDecimal getAnnualPrice() {
        return annualPrice;
    }

    public void setAnnualPrice(BigDecimal annualPrice) {
        this.annualPrice = annualPrice;
    }

    public Integer getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(Integer storageSpace) {
        this.storageSpace = storageSpace;
    }

    public Integer getAiQuota() {
        return aiQuota;
    }

    public void setAiQuota(Integer aiQuota) {
        this.aiQuota = aiQuota;
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
        return "SubscriptionPlan{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planDisplayName='" + planDisplayName + '\'' +
                ", monthlyPrice=" + monthlyPrice +
                ", annualPrice=" + annualPrice +
                ", storageSpace=" + storageSpace +
                ", aiQuota=" + aiQuota +
                '}';
    }
}
