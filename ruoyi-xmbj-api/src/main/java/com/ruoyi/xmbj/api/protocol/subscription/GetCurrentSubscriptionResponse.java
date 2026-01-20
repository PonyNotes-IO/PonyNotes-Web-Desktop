package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 获取当前订阅响应
 */
public class GetCurrentSubscriptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码
    private String message; // 返回消息
    private UserSubscription data; // 用户当前订阅信息

    public GetCurrentSubscriptionResponse() {
    }

    public GetCurrentSubscriptionResponse(Integer code, String message, UserSubscription data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserSubscription getData() {
        return data;
    }

    public void setData(UserSubscription data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetCurrentSubscriptionResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
