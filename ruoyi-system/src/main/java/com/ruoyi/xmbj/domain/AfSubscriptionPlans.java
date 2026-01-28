package com.ruoyi.xmbj.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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
    private Boolean hasInbox;

    /** 是否支持多设备同步 */
    @Excel(name = "是否支持多设备同步")
    private Boolean hasMultiDeviceSync;

    /** 是否支持API */
    @Excel(name = "是否支持API")
    private Boolean hasApiSupport;

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
    private Boolean hasShareLink;

    /** 是否支持发布 */
    @Excel(name = "是否支持发布")
    private Boolean hasPublish;

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
    private Boolean hasSpaceMemberManagement;

    /** 是否支持空间成员分组 */
    @Excel(name = "是否支持空间成员分组")
    private Boolean hasSpaceMemberGrouping;

    /** 是否启用（1是 0否） */
    @Excel(name = "是否启用", readConverterExp = "1=是,0=否")
    private Boolean isActive;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;


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
