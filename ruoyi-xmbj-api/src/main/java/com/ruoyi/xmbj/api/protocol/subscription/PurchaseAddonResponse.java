package com.ruoyi.xmbj.api.protocol.subscription;

import java.io.Serializable;

/**
 * 购买补充包响应
 */
public class PurchaseAddonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码
    private String message; // 返回消息
    private UserAddon data; // 用户补充包信息

    public PurchaseAddonResponse() {
    }

    public PurchaseAddonResponse(Integer code, String message, UserAddon data) {
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

    public UserAddon getData() {
        return data;
    }

    public void setData(UserAddon data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PurchaseAddonResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
