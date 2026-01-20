package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 用户使用情况（配额信息）
 */
public class UserUsage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private Integer storageQuota; // 总存储空间配额(GB)
    private Integer storageUsed; // 已使用存储(GB)
    private Integer storageRemaining; // 剩余存储(GB)
    private Integer aiCallQuota; // 总AI调用配额(次)
    private Integer aiCallUsed; // 已使用AI调用(次)
    private Integer aiCallRemaining; // 剩余AI调用(次)
    private Long updateTime;

    public UserUsage() {
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStorageQuota() {
        return storageQuota;
    }

    public void setStorageQuota(Integer storageQuota) {
        this.storageQuota = storageQuota;
    }

    public Integer getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(Integer storageUsed) {
        this.storageUsed = storageUsed;
    }

    public Integer getStorageRemaining() {
        return storageRemaining;
    }

    public void setStorageRemaining(Integer storageRemaining) {
        this.storageRemaining = storageRemaining;
    }

    public Integer getAiCallQuota() {
        return aiCallQuota;
    }

    public void setAiCallQuota(Integer aiCallQuota) {
        this.aiCallQuota = aiCallQuota;
    }

    public Integer getAiCallUsed() {
        return aiCallUsed;
    }

    public void setAiCallUsed(Integer aiCallUsed) {
        this.aiCallUsed = aiCallUsed;
    }

    public Integer getAiCallRemaining() {
        return aiCallRemaining;
    }

    public void setAiCallRemaining(Integer aiCallRemaining) {
        this.aiCallRemaining = aiCallRemaining;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserUsage{" +
                "storageUsed=" + storageUsed + "/" + storageQuota + " GB" +
                ", aiCallUsed=" + aiCallUsed + "/" + aiCallQuota + " times" +
                '}';
    }
}
