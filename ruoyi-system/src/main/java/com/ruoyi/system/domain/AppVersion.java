package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * App版本管理对象 app_version
 * 
 * @author 张继科
 * @date 2026-01-25
 */
public class AppVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键（序号） */
    private Long id;

    /** 版本名称（如V2.3.0） */
    @Excel(name = "版本名称", readConverterExp = "如=V2.3.0")
    private String versionName;

    /** 版本类型（如正式版、测试版、灰度版） */
    @Excel(name = "版本类型", readConverterExp = "如=正式版、测试版、灰度版")
    private String versionType;

    /** 版本号（整型，用于版本比较，如20300） */
    @Excel(name = "版本号", readConverterExp = "整=型，用于版本比较，如20300")
    private Long versionCode;

    /** 更新描述（文件链接） */
    @Excel(name = "更新描述", readConverterExp = "文=件链接")
    private String updateDesc;

    /** 更新描述文件路径 */
    @Excel(name = "更新描述文件路径")
    private String updateDescFile;

    /** 上传文件包（安装包）下载链接 */
    @Excel(name = "上传文件包", readConverterExp = "安=装包")
    private String packageUrl;

    /** 系统类型（android/ios） */
    @Excel(name = "系统类型", readConverterExp = "a=ndroid/ios")
    private String systemType;

    /** 状态（0-未发布 1-已发布 2-已停用） */
    @Excel(name = "状态", readConverterExp = "0=-未发布,1=-已发布,2=-已停用")
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 操作人名称 */
    @Excel(name = "操作人名称")
    private String operatorName;

    /** 删除标记（0-正常 1-已删除） */
    private Integer delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setVersionName(String versionName) 
    {
        this.versionName = versionName;
    }

    public String getVersionName() 
    {
        return versionName;
    }

    public void setVersionType(String versionType) 
    {
        this.versionType = versionType;
    }

    public String getVersionType() 
    {
        return versionType;
    }

    public void setVersionCode(Long versionCode) 
    {
        this.versionCode = versionCode;
    }

    public Long getVersionCode() 
    {
        return versionCode;
    }

    public void setUpdateDesc(String updateDesc) 
    {
        this.updateDesc = updateDesc;
    }

    public String getUpdateDesc() 
    {
        return updateDesc;
    }

    public void setUpdateDescFile(String updateDescFile) 
    {
        this.updateDescFile = updateDescFile;
    }

    public String getUpdateDescFile() 
    {
        return updateDescFile;
    }

    public void setPackageUrl(String packageUrl) 
    {
        this.packageUrl = packageUrl;
    }

    public String getPackageUrl() 
    {
        return packageUrl;
    }

    public void setSystemType(String systemType) 
    {
        this.systemType = systemType;
    }

    public String getSystemType() 
    {
        return systemType;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }

    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }

    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }

    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("versionName", getVersionName())
            .append("versionType", getVersionType())
            .append("versionCode", getVersionCode())
            .append("updateDesc", getUpdateDesc())
            .append("updateDescFile", getUpdateDescFile())
            .append("packageUrl", getPackageUrl())
            .append("systemType", getSystemType())
            .append("status", getStatus())
            .append("createdTime", getCreatedTime())
            .append("operator", getOperator())
            .append("operatorName", getOperatorName())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
