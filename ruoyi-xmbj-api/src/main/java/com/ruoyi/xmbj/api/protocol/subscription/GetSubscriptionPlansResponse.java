package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.util.List;

/**
 * 获取订阅计划响应
 */
public class GetSubscriptionPlansResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码：0成功，非0失败
    private String message; // 返回消息
    private List<SubscriptionPlan> data; // 订阅计划列表

    public GetSubscriptionPlansResponse() {
    }

    public GetSubscriptionPlansResponse(Integer code, String message, List<SubscriptionPlan> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
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

    public List<SubscriptionPlan> getData() {
        return data;
    }

    public void setData(List<SubscriptionPlan> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetSubscriptionPlansResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", dataSize=" + (data != null ? data.size() : 0) +
                '}';
    }
}
