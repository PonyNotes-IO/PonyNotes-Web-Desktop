package com.ruoyi.xmbj.domain;

import java.io.Serializable;

public abstract class XmBaseEntity implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
