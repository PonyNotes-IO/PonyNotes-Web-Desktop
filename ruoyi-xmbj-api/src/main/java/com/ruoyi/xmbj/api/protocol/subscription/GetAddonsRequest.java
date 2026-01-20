package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 获取补充包请求
 */
public class GetAddonsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String addonType; // 可选：过滤补充包类型（storage/ai_quota/priority等）
    private Boolean onlyActive; // 可选：仅获取活跃的补充包

    public GetAddonsRequest() {
    }

    public GetAddonsRequest(String addonType) {
        this.addonType = addonType;
        this.onlyActive = true;
    }

    public String getAddonType() {
        return addonType;
    }

    public void setAddonType(String addonType) {
        this.addonType = addonType;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }
}
