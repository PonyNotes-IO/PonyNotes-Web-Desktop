package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;
import java.util.List;

/**
 * 获取用户补充包列表响应
 */
public class GetMyAddonsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码
    private String message; // 返回消息
    private List<UserAddon> data; // 用户补充包列表

    public GetMyAddonsResponse() {
    }

    public GetMyAddonsResponse(Integer code, String message, List<UserAddon> data) {
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

    public List<UserAddon> getData() {
        return data;
    }

    public void setData(List<UserAddon> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetMyAddonsResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", dataSize=" + (data != null ? data.size() : 0) +
                '}';
    }
}
