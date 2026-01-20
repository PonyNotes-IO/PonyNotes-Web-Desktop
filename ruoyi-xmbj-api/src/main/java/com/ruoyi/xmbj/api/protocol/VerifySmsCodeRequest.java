package com.ruoyi.xmbj.api.protocol;

//import lombok.Data;

import java.io.Serializable;

//@Data
public class VerifySmsCodeRequest implements Serializable {

    private String phone;
    private String code;
    private String purpose;

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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
