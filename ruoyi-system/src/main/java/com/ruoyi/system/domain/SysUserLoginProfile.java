package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户登录概况对象 sys_user_login_profile
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public class SysUserLoginProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 登录概况ID（主键） */
    private Long profileId;

    /** 关联用户ID（外键，关联sys_user表的user_id） */
    @Excel(name = "关联用户ID", readConverterExp = "外=键，关联sys_user表的user_id")
    private Long userId;

    /** 用户来源（如App） */
    @Excel(name = "用户来源", readConverterExp = "如=App")
    private String userSource;

    /** 用户ID（如a111111） */
    @Excel(name = "用户ID", readConverterExp = "如=a111111")
    private String userUniqueId;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 登陆总次数 */
    @Excel(name = "登陆总次数")
    private Long loginTotalCount;

    /** 登陆总时长（如120时） */
    @Excel(name = "登陆总时长", readConverterExp = "如=120时")
    private String loginTotalDuration;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerTime;

    public void setProfileId(Long profileId) 
    {
        this.profileId = profileId;
    }

    public Long getProfileId() 
    {
        return profileId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUserSource(String userSource) 
    {
        this.userSource = userSource;
    }

    public String getUserSource() 
    {
        return userSource;
    }

    public void setUserUniqueId(String userUniqueId) 
    {
        this.userUniqueId = userUniqueId;
    }

    public String getUserUniqueId() 
    {
        return userUniqueId;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setLoginTotalCount(Long loginTotalCount) 
    {
        this.loginTotalCount = loginTotalCount;
    }

    public Long getLoginTotalCount() 
    {
        return loginTotalCount;
    }

    public void setLoginTotalDuration(String loginTotalDuration) 
    {
        this.loginTotalDuration = loginTotalDuration;
    }

    public String getLoginTotalDuration() 
    {
        return loginTotalDuration;
    }

    public void setRegisterTime(Date registerTime) 
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("profileId", getProfileId())
            .append("userId", getUserId())
            .append("userSource", getUserSource())
            .append("userUniqueId", getUserUniqueId())
            .append("phone", getPhone())
            .append("loginTotalCount", getLoginTotalCount())
            .append("loginTotalDuration", getLoginTotalDuration())
            .append("registerTime", getRegisterTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
