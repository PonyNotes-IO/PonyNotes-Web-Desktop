package com.ruoyi.xmbj.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SignInLogStatistics {
//    用户来源
    private String userSource;

//    用户ID
    private String userId;
//    手机号
    private String phone;

//    登陆总次数
    private Integer signCount;

//    登陆总时长
    private String totalTime;

//    注册时间
    private Date registerTime;
}
