package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户登录记录对象 sys_user_login_record
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public class SysUserLoginRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID（主键） */
    private Long recordId;

    /** 关联登录概况ID（外键） */
    @Excel(name = "关联登录概况ID", readConverterExp = "外=键")
    private Long profileId;

    /** 登陆时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登陆时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginTime;

    /** 登陆地点 */
    @Excel(name = "登陆地点")
    private String loginLocation;

    /** 登陆IP */
    @Excel(name = "登陆IP")
    private String loginIp;

    /** 登陆方式（如微信） */
    @Excel(name = "登陆方式", readConverterExp = "如=微信")
    private String loginMethod;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }

    public void setProfileId(Long profileId) 
    {
        this.profileId = profileId;
    }

    public Long getProfileId() 
    {
        return profileId;
    }

    public void setLoginTime(Date loginTime) 
    {
        this.loginTime = loginTime;
    }

    public Date getLoginTime() 
    {
        return loginTime;
    }

    public void setLoginLocation(String loginLocation) 
    {
        this.loginLocation = loginLocation;
    }

    public String getLoginLocation() 
    {
        return loginLocation;
    }

    public void setLoginIp(String loginIp) 
    {
        this.loginIp = loginIp;
    }

    public String getLoginIp() 
    {
        return loginIp;
    }

    public void setLoginMethod(String loginMethod) 
    {
        this.loginMethod = loginMethod;
    }

    public String getLoginMethod() 
    {
        return loginMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("profileId", getProfileId())
            .append("loginTime", getLoginTime())
            .append("loginLocation", getLoginLocation())
            .append("loginIp", getLoginIp())
            .append("loginMethod", getLoginMethod())
            .toString();
    }
}
