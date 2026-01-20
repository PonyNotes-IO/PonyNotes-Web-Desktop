package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 通用API响应基类
 */
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码：0成功，非0失败
    private String message; // 返回消息
    private T data; // 响应数据

    public BaseResponse() {
    }

    public BaseResponse(Integer code, String message, T data) {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断是否成功（code为0表示成功）
     */
    public boolean isSuccess() {
        return code != null && code == 0;
    }

    /**
     * 获取错误信息（当请求失败时）
     */
    public String getErrorMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
