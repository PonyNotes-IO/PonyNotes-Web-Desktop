package com.ruoyi.xmbj.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订阅套餐对象 af_subscription_plans
 * 
 * @author 张继科
 * @date 2026-01-21
 */
public class AfSubscriptionPlans extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套餐ID */
    private Long id;

    /** 套餐编码 */
    @Excel(name = "套餐编码")
    private String planCode;

    /** 套餐名称（英文） */
    @Excel(name = "套餐名称", readConverterExp = "英=文")
    private String planName;

    /** 套餐名称（中文） */
    @Excel(name = "套餐名称", readConverterExp = "中=文")
    private String planNameCn;

    /** 月付价格（元） */
    @Excel(name = "月付价格", readConverterExp = "元=")
    private BigDecimal monthlyPriceYuan;

    /** 年付价格（元） */
    @Excel(name = "年付价格", readConverterExp = "元=")
    private BigDecimal yearlyPriceYuan;

    /** 云存储容量（GB） */
    @Excel(name = "云存储容量", readConverterExp = "G=B")
    private Long cloudStorageGb;

    /** 是否支持收件箱（0否 1是） */
    @Excel(name = "是否支持收件箱", readConverterExp = "0=否,1=是")
    private Integer hasInbox;

    /** 是否支持多设备同步 */
    @Excel(name = "是否支持多设备同步")
    private Integer hasMultiDeviceSync;

    /** 是否支持API */
    @Excel(name = "是否支持API")
    private Integer hasApiSupport;

    /** 版本历史保留天数 */
    @Excel(name = "版本历史保留天数")
    private Long versionHistoryDays;

    /** 每月AI聊天次数 */
    @Excel(name = "每月AI聊天次数")
    private Long aiChatCountPerMonth;

    /** 每月AI生成图片次数 */
    @Excel(name = "每月AI生成图片次数")
    private Long aiImageGenerationPerMonth;

    /** 是否支持分享链接 */
    @Excel(name = "是否支持分享链接")
    private Integer hasShareLink;

    /** 是否支持发布 */
    @Excel(name = "是否支持发布")
    private Integer hasPublish;

    /** 工作空间成员上限 */
    @Excel(name = "工作空间成员上限")
    private Long workspaceMemberLimit;

    /** 协作工作空间上限 */
    @Excel(name = "协作工作空间上限")
    private Long collaborativeWorkspaceLimit;

    /** 页面权限访客编辑者上限 */
    @Excel(name = "页面权限访客编辑者上限")
    private Long pagePermissionGuestEditors;

    /** 是否支持空间成员管理 */
    @Excel(name = "是否支持空间成员管理")
    private Integer hasSpaceMemberManagement;

    /** 是否支持空间成员分组 */
    @Excel(name = "是否支持空间成员分组")
    private Integer hasSpaceMemberGrouping;

    /** 是否启用（1是 0否） */
    @Excel(name = "是否启用", readConverterExp = "1=是,0=否")
    private Integer isActive;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setPlanCode(String planCode) 
    {
        this.planCode = planCode;
    }

    public String getPlanCode() 
    {
        return planCode;
    }

    public void setPlanName(String planName) 
    {
        this.planName = planName;
    }

    public String getPlanName() 
    {
        return planName;
    }

    public void setPlanNameCn(String planNameCn) 
    {
        this.planNameCn = planNameCn;
    }

    public String getPlanNameCn() 
    {
        return planNameCn;
    }

    public void setMonthlyPriceYuan(BigDecimal monthlyPriceYuan) 
    {
        this.monthlyPriceYuan = monthlyPriceYuan;
    }

    public BigDecimal getMonthlyPriceYuan() 
    {
        return monthlyPriceYuan;
    }

    public void setYearlyPriceYuan(BigDecimal yearlyPriceYuan) 
    {
        this.yearlyPriceYuan = yearlyPriceYuan;
    }

    public BigDecimal getYearlyPriceYuan() 
    {
        return yearlyPriceYuan;
    }

    public void setCloudStorageGb(Long cloudStorageGb) 
    {
        this.cloudStorageGb = cloudStorageGb;
    }

    public Long getCloudStorageGb() 
    {
        return cloudStorageGb;
    }

    public void setHasInbox(Integer hasInbox) 
    {
        this.hasInbox = hasInbox;
    }

    public Integer getHasInbox() 
    {
        return hasInbox;
    }

    public void setHasMultiDeviceSync(Integer hasMultiDeviceSync) 
    {
        this.hasMultiDeviceSync = hasMultiDeviceSync;
    }

    public Integer getHasMultiDeviceSync() 
    {
        return hasMultiDeviceSync;
    }

    public void setHasApiSupport(Integer hasApiSupport) 
    {
        this.hasApiSupport = hasApiSupport;
    }

    public Integer getHasApiSupport() 
    {
        return hasApiSupport;
    }

    public void setVersionHistoryDays(Long versionHistoryDays) 
    {
        this.versionHistoryDays = versionHistoryDays;
    }

    public Long getVersionHistoryDays() 
    {
        return versionHistoryDays;
    }

    public void setAiChatCountPerMonth(Long aiChatCountPerMonth) 
    {
        this.aiChatCountPerMonth = aiChatCountPerMonth;
    }

    public Long getAiChatCountPerMonth() 
    {
        return aiChatCountPerMonth;
    }

    public void setAiImageGenerationPerMonth(Long aiImageGenerationPerMonth) 
    {
        this.aiImageGenerationPerMonth = aiImageGenerationPerMonth;
    }

    public Long getAiImageGenerationPerMonth() 
    {
        return aiImageGenerationPerMonth;
    }

    public void setHasShareLink(Integer hasShareLink) 
    {
        this.hasShareLink = hasShareLink;
    }

    public Integer getHasShareLink() 
    {
        return hasShareLink;
    }

    public void setHasPublish(Integer hasPublish) 
    {
        this.hasPublish = hasPublish;
    }

    public Integer getHasPublish() 
    {
        return hasPublish;
    }

    public void setWorkspaceMemberLimit(Long workspaceMemberLimit) 
    {
        this.workspaceMemberLimit = workspaceMemberLimit;
    }

    public Long getWorkspaceMemberLimit() 
    {
        return workspaceMemberLimit;
    }

    public void setCollaborativeWorkspaceLimit(Long collaborativeWorkspaceLimit) 
    {
        this.collaborativeWorkspaceLimit = collaborativeWorkspaceLimit;
    }

    public Long getCollaborativeWorkspaceLimit() 
    {
        return collaborativeWorkspaceLimit;
    }

    public void setPagePermissionGuestEditors(Long pagePermissionGuestEditors) 
    {
        this.pagePermissionGuestEditors = pagePermissionGuestEditors;
    }

    public Long getPagePermissionGuestEditors() 
    {
        return pagePermissionGuestEditors;
    }

    public void setHasSpaceMemberManagement(Integer hasSpaceMemberManagement) 
    {
        this.hasSpaceMemberManagement = hasSpaceMemberManagement;
    }

    public Integer getHasSpaceMemberManagement() 
    {
        return hasSpaceMemberManagement;
    }

    public void setHasSpaceMemberGrouping(Integer hasSpaceMemberGrouping) 
    {
        this.hasSpaceMemberGrouping = hasSpaceMemberGrouping;
    }

    public Integer getHasSpaceMemberGrouping() 
    {
        return hasSpaceMemberGrouping;
    }

    public void setIsActive(Integer isActive) 
    {
        this.isActive = isActive;
    }

    public Integer getIsActive() 
    {
        return isActive;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
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
            .append("pagePermissionGuestEditors", getPagePermissionGuestEditors())
            .append("hasSpaceMemberManagement", getHasSpaceMemberManagement())
            .append("hasSpaceMemberGrouping", getHasSpaceMemberGrouping())
            .append("isActive", getIsActive())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
