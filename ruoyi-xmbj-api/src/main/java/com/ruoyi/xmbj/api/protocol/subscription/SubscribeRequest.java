package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 创建/更新订阅请求
 */
public class SubscribeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long planId; // 订阅计划ID（必填）
    private String billingType; // 计费类型：monthly/annual（必填）

    public SubscribeRequest() {
    }

    public SubscribeRequest(Long planId, String billingType) {
        this.planId = planId;
        this.billingType = billingType;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    @Override
    public String toString() {
        return "SubscribeRequest{" +
                "planId=" + planId +
                ", billingType='" + billingType + '\'' +
                '}';
    }
}
