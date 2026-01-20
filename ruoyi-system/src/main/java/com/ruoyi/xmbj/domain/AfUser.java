package com.ruoyi.xmbj.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户管理11对象 af_user
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
public class AfUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID（自增主键） */
    private Long uid;

    /** 用户唯一标识（UUID） */
    @Excel(name = "用户唯一标识", readConverterExp = "U=UID")
    private String uuid;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 用户密码（加密存储） */
    @Excel(name = "用户密码", readConverterExp = "加=密存储")
    private String password;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String name;

    /** 用户头像（链接地址） */
    @Excel(name = "用户头像", readConverterExp = "链=接地址")
    private String avatar;

    /** 用户元数据（JSON格式） */
    @Excel(name = "用户元数据", readConverterExp = "J=SON格式")
    private String metadata;

    /** 加密签名 */
    @Excel(name = "加密签名")
    private String encryptionSign;

    /** 软删除时间（NULL表示未删除） */
    @Excel(name = "软删除时间", readConverterExp = "N=ULL表示未删除")
    private Date deletedAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String phone;

    public void setUid(Long uid) 
    {
        this.uid = uid;
    }

    public Long getUid() 
    {
        return uid;
    }

    public void setUuid(String uuid) 
    {
        this.uuid = uuid;
    }

    public String getUuid() 
    {
        return uuid;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }

    public void setMetadata(String metadata) 
    {
        this.metadata = metadata;
    }

    public String getMetadata() 
    {
        return metadata;
    }

    public void setEncryptionSign(String encryptionSign) 
    {
        this.encryptionSign = encryptionSign;
    }

    public String getEncryptionSign() 
    {
        return encryptionSign;
    }

    public void setDeletedAt(Date deletedAt) 
    {
        this.deletedAt = deletedAt;
    }

    public Date getDeletedAt() 
    {
        return deletedAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uid", getUid())
            .append("uuid", getUuid())
            .append("email", getEmail())
            .append("password", getPassword())
            .append("name", getName())
            .append("avatar", getAvatar())
            .append("metadata", getMetadata())
            .append("encryptionSign", getEncryptionSign())
            .append("deletedAt", getDeletedAt())
            .append("updatedAt", getUpdatedAt())
            .append("createdAt", getCreatedAt())
            .append("phone", getPhone())
            .toString();
    }
}
