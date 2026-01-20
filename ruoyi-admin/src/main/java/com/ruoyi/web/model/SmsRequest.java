package com.ruoyi.web.model;

public class SmsRequest {
    private String phoneNumber; // 

    private String code; // 验证码
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
    
}
