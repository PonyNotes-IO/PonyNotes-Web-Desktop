package com.ruoyi.xmbj.api.protocol;

//import lombok.Data;

import java.io.Serializable;

//@Data
public class SendSmsCodeResponse implements Serializable {

//    @JSONField(name = "request_id")
    private String request_id;

    private String message;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
