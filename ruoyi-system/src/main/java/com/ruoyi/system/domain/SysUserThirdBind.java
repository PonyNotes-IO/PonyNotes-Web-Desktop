package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 第三方登录绑定对象 sys_user_third_bind
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public class SysUserThirdBind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绑定ID（主键） */
    private Long bindId;

    /** 关联登录概况ID（外键） */
    @Excel(name = "关联登录概况ID", readConverterExp = "外=键")
    private Long profileId;

    /** 第三方平台名称（如新浪微博） */
    @Excel(name = "第三方平台名称", readConverterExp = "如=新浪微博")
    private String thirdPlatform;

    /** 用户第三方标识 */
    @Excel(name = "用户第三方标识")
    private String thirdUserId;

    /** 最后登陆时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickname;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    public void setBindId(Long bindId) 
    {
        this.bindId = bindId;
    }

    public Long getBindId() 
    {
        return bindId;
    }

    public void setProfileId(Long profileId) 
    {
        this.profileId = profileId;
    }

    public Long getProfileId() 
    {
        return profileId;
    }

    public void setThirdPlatform(String thirdPlatform) 
    {
        this.thirdPlatform = thirdPlatform;
    }

    public String getThirdPlatform() 
    {
        return thirdPlatform;
    }

    public void setThirdUserId(String thirdUserId) 
    {
        this.thirdUserId = thirdUserId;
    }

    public String getThirdUserId() 
    {
        return thirdUserId;
    }

    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }

    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bindId", getBindId())
            .append("profileId", getProfileId())
            .append("thirdPlatform", getThirdPlatform())
            .append("thirdUserId", getThirdUserId())
            .append("lastLoginTime", getLastLoginTime())
            .append("nickname", getNickname())
            .append("gender", getGender())
            .toString();
    }
}
