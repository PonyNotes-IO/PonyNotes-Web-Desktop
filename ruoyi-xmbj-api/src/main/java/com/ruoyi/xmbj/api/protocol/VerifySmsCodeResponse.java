package com.ruoyi.xmbj.api.protocol;

//import lombok.Data;

import java.io.Serializable;

//@Data
public class VerifySmsCodeResponse implements Serializable {

    private Boolean success;

    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
