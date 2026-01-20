package com.ruoyi.web.model;

public class LoginVo {
    /**
     * 登录类型：code 验证码，password-密码登录
     */
    private String loginType;

    /**
     * 账号类型：phone 手机，email 邮箱
     */
    private String accountType;



    /**
     * 手机号（登录类型为phone时必填）
     */
    private String phone;

    /**
     * 邮箱（登录类型为email时必填）
     */
    private String email;

    /**
     * 用户名（登录类型为password时必填）
     */
    private String username;

    /**
     * 密码（登录类型为password时必填）
     */
    private String password;

    /**
     * 验证码（登录类型为phone或email时必填）
     */
    private String code;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "loginType='" + loginType + '\'' +
                ", accountType='" + accountType + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

