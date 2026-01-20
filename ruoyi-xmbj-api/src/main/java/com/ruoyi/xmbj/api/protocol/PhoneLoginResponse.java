package com.ruoyi.xmbj.api.protocol;

//import lombok.Data;

import java.io.Serializable;

//@Data
public class PhoneLoginResponse implements Serializable {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private Long expires_at;
    private String refresh_token;
    private PhoneLoginUser user;
    private String provider_access_token;
    private String provider_refresh_token;

//    @Data
    public static class PhoneLoginUser  implements Serializable{
        private String  id;
        private String  email;
        private String  created_at;
        private String  updated_at;
        private String  user_metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_metadata() {
        return user_metadata;
    }

    public void setUser_metadata(String user_metadata) {
        this.user_metadata = user_metadata;
    }
}

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public Long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Long expires_at) {
        this.expires_at = expires_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public PhoneLoginUser getUser() {
        return user;
    }

    public void setUser(PhoneLoginUser user) {
        this.user = user;
    }

    public String getProvider_access_token() {
        return provider_access_token;
    }

    public void setProvider_access_token(String provider_access_token) {
        this.provider_access_token = provider_access_token;
    }

    public String getProvider_refresh_token() {
        return provider_refresh_token;
    }

    public void setProvider_refresh_token(String provider_refresh_token) {
        this.provider_refresh_token = provider_refresh_token;
    }
}
