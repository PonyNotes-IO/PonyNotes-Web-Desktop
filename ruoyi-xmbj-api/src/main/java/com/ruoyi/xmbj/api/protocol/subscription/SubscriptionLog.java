package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 订阅日志（用于查询订阅记录）
 */
public class SubscriptionLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long logId;
    private String userId;
    private String logType; // 日志类型：subscription/cancel/purchase等
    private String description; // 描述
    private Long createTime;

    public SubscriptionLog() {
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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
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
        return "SubscriptionLog{" +
                "logId=" + logId +
                ", logType='" + logType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
