package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 获取订阅计划请求
 */
public class GetSubscriptionPlansRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 可选：是否仅获取活跃的计划
    private Boolean onlyActive;

    public GetSubscriptionPlansRequest() {
    }

    public GetSubscriptionPlansRequest(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }
}
