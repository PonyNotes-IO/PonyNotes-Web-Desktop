package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 使用日志
 */
public class UsageLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long logId;
    private String userId;
    private String usageType; // 使用类型：storage/ai_call等
    private Integer quantity; // 使用量
    private String description; // 使用描述
    private Long createTime;

    public UsageLog() {
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

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
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
        return "UsageLog{" +
                "logId=" + logId +
                ", usageType='" + usageType + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
