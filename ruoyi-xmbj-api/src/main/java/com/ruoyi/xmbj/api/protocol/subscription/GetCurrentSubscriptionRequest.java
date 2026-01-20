package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 获取当前订阅请求
 */
public class GetCurrentSubscriptionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 无需额外参数，使用用户Token中的用户ID

    public GetCurrentSubscriptionRequest() {
    }
}
