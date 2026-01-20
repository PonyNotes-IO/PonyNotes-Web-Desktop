package com.ruoyi.xmbj.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ThirdPartySign {
    /**
     * 序号
     */
    private String thirdPartId;

    /**
     * 用户uid
     */
    private String userUid;


    /**
     * 用户最后一次登录ID
     */
    private String lastSignId;


    /**
     * 第三方登陆名称
     */
    private String thirdPartLoginName;

    /**
     * 用户第三方标识
     */
    private String thirdPartIdentifier;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

    /**
     * 昵称
     */
    private String nickname;

    /**
    * 性别
    */
    private String gender;

}
