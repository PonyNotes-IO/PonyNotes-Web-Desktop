package com.ruoyi.web.model;

import com.ruoyi.common.core.domain.entity.SysUser;

public class AccountInfoVo {


    /**
     * 手机号（登录类型为phone时必填）
     */
    private String phone;
    /**
     * 邮箱（登录类型为email时必填）
     */
    private String email;

    private Boolean phoneBind; 

    private Boolean emailBind;

    private Boolean passwordSet;

    private SysUser user;


    


}

