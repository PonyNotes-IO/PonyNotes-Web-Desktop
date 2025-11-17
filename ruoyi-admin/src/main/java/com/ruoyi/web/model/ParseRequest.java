package com.ruoyi.web.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ParseRequest implements Serializable {

    private MultipartFile doc;

    private String type;

    public MultipartFile getDoc() {
        return doc;
    }

    public void setDoc(MultipartFile doc) {
        this.doc = doc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
