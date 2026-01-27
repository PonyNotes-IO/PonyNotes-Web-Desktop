package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.util.List;

/**
 * 获取补充包响应
 */
public class GetAddonsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码：0成功，非0失败
    private String message; // 返回消息
    private List<SubscriptionAddon> data; // 补充包列表

    public GetAddonsResponse() {
    }

    public GetAddonsResponse(Integer code, String message, List<SubscriptionAddon> data) {
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

    public List<SubscriptionAddon> getData() {
        return data;
    }

    public void setData(List<SubscriptionAddon> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetAddonsResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", dataSize=" + (data != null ? data.size() : 0) +
                '}';
    }
}
