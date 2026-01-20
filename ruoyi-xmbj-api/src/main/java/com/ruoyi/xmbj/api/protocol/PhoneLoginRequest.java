package com.ruoyi.xmbj.api.protocol;

import java.io.Serializable;

//@Data
public class PhoneLoginRequest implements Serializable {

    private String phone;
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
