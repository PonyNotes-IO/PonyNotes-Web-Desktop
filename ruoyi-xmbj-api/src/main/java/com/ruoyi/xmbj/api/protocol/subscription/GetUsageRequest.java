package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 获取用户使用情况请求
 */
public class GetUsageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 无需额外参数，使用用户Token中的用户ID

    public GetUsageRequest() {
    }
}
