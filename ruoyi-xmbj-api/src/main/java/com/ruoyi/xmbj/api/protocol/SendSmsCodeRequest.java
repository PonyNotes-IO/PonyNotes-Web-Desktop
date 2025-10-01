package com.ruoyi.xmbj.api.protocol;

//import lombok.Data;

import java.io.Serializable;

//@Data
public class SendSmsCodeRequest implements Serializable {

    private String phone;
    private String purpose;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
