package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 记录使用情况响应
 */
public class RecordUsageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码
    private String message; // 返回消息
    private UserUsage data; // 更新后的用户使用情况

    public RecordUsageResponse() {
    }

    public RecordUsageResponse(Integer code, String message, UserUsage data) {
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

    public UserUsage getData() {
        return data;
    }

    public void setData(UserUsage data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RecordUsageResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
