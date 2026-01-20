package com.ruoyi.system.domain;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;

/**
 * 套餐信息表 sys_plans
 * 
 * @author ruoyi
 */
public class SysPlans extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套餐ID */
    @Excel(name = "套餐ID", cellType = ColumnType.NUMERIC)
    private Long id;

    /** 套餐编码 */
    @Excel(name = "套餐编码")
    private String planCode;

    /** 套餐名称(英文) */
    @Excel(name = "套餐名称(英文)")
    private String planName;

    /** 套餐名称(中文) */
    @Excel(name = "套餐名称(中文)")
    private String planNameCn;

    /** 月付价格(元) */
    @Excel(name = "月付价格(元)", cellType = ColumnType.NUMERIC)
    private BigDecimal monthlyPriceYuan;

    /** 年付价格(元) */
    @Excel(name = "年付价格(元)", cellType = ColumnType.NUMERIC)
    private BigDecimal yearlyPriceYuan;

    /** 云存储容量(GB) */
    @Excel(name = "云存储容量(GB)", cellType = ColumnType.NUMERIC)
    private Integer cloudStorageGb;

    /** 是否支持收件箱(0:不支持,1:支持) */
    @Excel(name = "是否支持收件箱", readConverterExp = "0=不支持,1=支持")
    private Integer hasInbox;

    /** 是否支持多设备同步(0:不支持,1:支持) */
    @Excel(name = "是否支持多设备同步", readConverterExp = "0=不支持,1=支持")
    private Integer hasMultiDeviceSync;

    /** 是否支持API(0:不支持,1:支持) */
    @Excel(name = "是否支持API", readConverterExp = "0=不支持,1=支持")
    private Integer hasApiSupport;

    /** 版本历史保留天数 */
    @Excel(name = "版本历史保留天数", cellType = ColumnType.NUMERIC)
    private Integer versionHistoryDays;

    /** 每月AI聊天次数限制(0:无限制) */
    @Excel(name = "每月AI聊天次数限制", cellType = ColumnType.NUMERIC)
    private Integer aiChatCountPerMonth;

    /** 每月AI图片生成次数限制(0:无限制) */
    @Excel(name = "每月AI图片生成次数限制", cellType = ColumnType.NUMERIC)
    private Integer aiImageGenerationPerMonth;

    /** 是否支持分享链接(0:不支持,1:支持) */
    @Excel(name = "是否支持分享链接", readConverterExp = "0=不支持,1=支持")
    private Integer hasShareLink;

    /** 是否支持发布功能(0:不支持,1:支持) */
    @Excel(name = "是否支持发布功能", readConverterExp = "0=不支持,1=支持")
    private Integer hasPublish;

    /** 工作区成员限制(0:无限制) */
    @Excel(name = "工作区成员限制", cellType = ColumnType.NUMERIC)
    private Integer workspaceMemberLimit;

    /** 协作工作区限制(0:无限制) */
    @Excel(name = "协作工作区限制", cellType = ColumnType.NUMERIC)
    private Integer collaborativeWorkspaceLimit;

    /** 是否支持空间成员管理(0:不支持,1:支持) */
    @Excel(name = "是否支持空间成员管理", readConverterExp = "0=不支持,1=支持")
    private Integer hasSpaceMemberManagement;

    /** 是否支持空间成员分组(0:不支持,1:支持) */
    @Excel(name = "是否支持空间成员分组", readConverterExp = "0=不支持,1=支持")
    private Integer hasSpaceMemberGrouping;

    /** 是否启用(0:禁用,1:启用) */
    @Excel(name = "是否启用", readConverterExp = "0=禁用,1=启用")
    private Integer isActive;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPlanCode()
    {
        return planCode;
    }

    public void setPlanCode(String planCode)
    {
        this.planCode = planCode;
    }

    public String getPlanName()
    {
        return planName;
    }

    public void setPlanName(String planName)
    {
        this.planName = planName;
    }

    public String getPlanNameCn()
    {
        return planNameCn;
    }

    public void setPlanNameCn(String planNameCn)
    {
        this.planNameCn = planNameCn;
    }

    public BigDecimal getMonthlyPriceYuan()
    {
        return monthlyPriceYuan;
    }

    public void setMonthlyPriceYuan(BigDecimal monthlyPriceYuan)
    {
        this.monthlyPriceYuan = monthlyPriceYuan;
    }

    public BigDecimal getYearlyPriceYuan()
    {
        return yearlyPriceYuan;
    }

    public void setYearlyPriceYuan(BigDecimal yearlyPriceYuan)
    {
        this.yearlyPriceYuan = yearlyPriceYuan;
    }

    public Integer getCloudStorageGb()
    {
        return cloudStorageGb;
    }

    public void setCloudStorageGb(Integer cloudStorageGb)
    {
        this.cloudStorageGb = cloudStorageGb;
    }

    public Integer getHasInbox()
    {
        return hasInbox;
    }

    public void setHasInbox(Integer hasInbox)
    {
        this.hasInbox = hasInbox;
    }

    public Integer getHasMultiDeviceSync()
    {
        return hasMultiDeviceSync;
    }

    public void setHasMultiDeviceSync(Integer hasMultiDeviceSync)
    {
        this.hasMultiDeviceSync = hasMultiDeviceSync;
    }

    public Integer getHasApiSupport()
    {
        return hasApiSupport;
    }

    public void setHasApiSupport(Integer hasApiSupport)
    {
        this.hasApiSupport = hasApiSupport;
    }

    public Integer getVersionHistoryDays()
    {
        return versionHistoryDays;
    }

    public void setVersionHistoryDays(Integer versionHistoryDays)
    {
        this.versionHistoryDays = versionHistoryDays;
    }

    public Integer getAiChatCountPerMonth()
    {
        return aiChatCountPerMonth;
    }

    public void setAiChatCountPerMonth(Integer aiChatCountPerMonth)
    {
        this.aiChatCountPerMonth = aiChatCountPerMonth;
    }

    public Integer getAiImageGenerationPerMonth()
    {
        return aiImageGenerationPerMonth;
    }

    public void setAiImageGenerationPerMonth(Integer aiImageGenerationPerMonth)
    {
        this.aiImageGenerationPerMonth = aiImageGenerationPerMonth;
    }

    public Integer getHasShareLink()
    {
        return hasShareLink;
    }

    public void setHasShareLink(Integer hasShareLink)
    {
        this.hasShareLink = hasShareLink;
    }

    public Integer getHasPublish()
    {
        return hasPublish;
    }

    public void setHasPublish(Integer hasPublish)
    {
        this.hasPublish = hasPublish;
    }

    public Integer getWorkspaceMemberLimit()
    {
        return workspaceMemberLimit;
    }

    public void setWorkspaceMemberLimit(Integer workspaceMemberLimit)
    {
        this.workspaceMemberLimit = workspaceMemberLimit;
    }

    public Integer getCollaborativeWorkspaceLimit()
    {
        return collaborativeWorkspaceLimit;
    }

    public void setCollaborativeWorkspaceLimit(Integer collaborativeWorkspaceLimit)
    {
        this.collaborativeWorkspaceLimit = collaborativeWorkspaceLimit;
    }

    public Integer getHasSpaceMemberManagement()
    {
        return hasSpaceMemberManagement;
    }

    public void setHasSpaceMemberManagement(Integer hasSpaceMemberManagement)
    {
        this.hasSpaceMemberManagement = hasSpaceMemberManagement;
    }

    public Integer getHasSpaceMemberGrouping()
    {
        return hasSpaceMemberGrouping;
    }

    public void setHasSpaceMemberGrouping(Integer hasSpaceMemberGrouping)
    {
        this.hasSpaceMemberGrouping = hasSpaceMemberGrouping;
    }

    public Integer getIsActive()
    {
        return isActive;
    }

    public void setIsActive(Integer isActive)
    {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planCode", getPlanCode())
            .append("planName", getPlanName())
            .append("planNameCn", getPlanNameCn())
            .append("monthlyPriceYuan", getMonthlyPriceYuan())
            .append("yearlyPriceYuan", getYearlyPriceYuan())
            .append("cloudStorageGb", getCloudStorageGb())
            .append("hasInbox", getHasInbox())
            .append("hasMultiDeviceSync", getHasMultiDeviceSync())
            .append("hasApiSupport", getHasApiSupport())
            .append("versionHistoryDays", getVersionHistoryDays())
            .append("aiChatCountPerMonth", getAiChatCountPerMonth())
            .append("aiImageGenerationPerMonth", getAiImageGenerationPerMonth())
            .append("hasShareLink", getHasShareLink())
            .append("hasPublish", getHasPublish())
            .append("workspaceMemberLimit", getWorkspaceMemberLimit())
            .append("collaborativeWorkspaceLimit", getCollaborativeWorkspaceLimit())
            .append("hasSpaceMemberManagement", getHasSpaceMemberManagement())
            .append("hasSpaceMemberGrouping", getHasSpaceMemberGrouping())
            .append("isActive", getIsActive())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}