package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.util.List;

/**
 * 获取用户补充包列表请求
 */
public class GetMyAddonsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 可选：按状态过滤（active/expired/used）
    private String status;

    public GetMyAddonsRequest() {
    }

    public GetMyAddonsRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
