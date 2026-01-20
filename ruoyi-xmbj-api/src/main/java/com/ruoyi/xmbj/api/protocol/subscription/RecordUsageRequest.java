package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 记录使用情况请求（记录用户的AI调用、存储使用等）
 */
public class RecordUsageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String usageType; // 使用类型：storage/ai_call
    private Integer quantity; // 使用量
    private String description; // 使用描述

    public RecordUsageRequest() {
    }

    public RecordUsageRequest(String usageType, Integer quantity, String description) {
        this.usageType = usageType;
        this.quantity = quantity;
        this.description = description;
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

    @Override
    public String toString() {
        return "RecordUsageRequest{" +
                "usageType='" + usageType + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
