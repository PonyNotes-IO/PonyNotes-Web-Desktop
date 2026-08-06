package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 下载链接配置对象 download_link
 * 用于配置 xmbj-www-ui 中 /download 页面各平台的下载链接
 *
 * @author 张继科
 * @date 2026-08-05
 */
public class DownloadLink extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 平台（Windows、macOS、Android/Pad、iPhone/iPad） */
    @Excel(name = "平台")
    private String platform;

    /** 架构标签（如：Intel 芯片版 (x86)，仅多架构平台需要填写） */
    @Excel(name = "架构标签")
    private String archLabel;

    /** 架构描述（如：适用于 Intel 处理器的 Mac） */
    @Excel(name = "架构描述")
    private String archDesc;

    /** 版本号（如：1.0.0） */
    @Excel(name = "版本号")
    private String version;

    /** 下载链接 */
    @Excel(name = "下载链接")
    private String downloadUrl;

    /** 排序（数字越小越靠前） */
    @Excel(name = "排序")
    private Integer sortOrder;

    /** 状态（0-禁用 1-启用） */
    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用")
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /** 操作人（用户ID） */
    @Excel(name = "操作人")
    private String operator;

    /** 操作人名称（关联 sys_user 查询） */
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

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setArchLabel(String archLabel)
    {
        this.archLabel = archLabel;
    }

    public String getArchLabel()
    {
        return archLabel;
    }

    public void setArchDesc(String archDesc)
    {
        this.archDesc = archDesc;
    }

    public String getArchDesc()
    {
        return archDesc;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }

    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("platform", getPlatform())
            .append("archLabel", getArchLabel())
            .append("archDesc", getArchDesc())
            .append("version", getVersion())
            .append("downloadUrl", getDownloadUrl())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createdTime", getCreatedTime())
            .append("operator", getOperator())
            .append("operatorName", getOperatorName())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
