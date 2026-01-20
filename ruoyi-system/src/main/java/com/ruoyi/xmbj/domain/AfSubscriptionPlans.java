package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 订阅套餐表实体
 * @author ruoyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_subscription_plans")
public class AfSubscriptionPlans implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /** 套餐编码 */
    @Column(name = "plan_code")
    private String planCode;

    /** 套餐名称（英文） */
    @Column(name = "plan_name")
    private String planName;

    /** 套餐名称（中文） */
    @Column(name = "plan_name_cn")
    private String planNameCn;

    /** 月付价格（元） */
    @Column(name = "monthly_price_yuan")
    private BigDecimal monthlyPriceYuan;

    /** 年付价格（元） */
    @Column(name = "yearly_price_yuan")
    private BigDecimal yearlyPriceYuan;

    /** 云存储容量（GB） */
    @Column(name = "cloud_storage_gb")
    private Integer cloudStorageGb;

    /** 是否包含收件箱功能 */
    @Column(name = "has_inbox")
    private Boolean hasInbox;

    /** 是否支持多设备同步 */
    @Column(name = "has_multi_device_sync")
    private Boolean hasMultiDeviceSync;

    /** 是否支持API */
    @Column(name = "has_api_support")
    private Boolean hasApiSupport;

    /** 版本历史天数 */
    @Column(name = "version_history_days")
    private Integer versionHistoryDays;

    /** 每月AI聊天次数 */
    @Column(name = "chat_count_per_month")
    private Integer chatCountPerMonth;

    /** 每月AI图片生成次数 */
    @Column(name = "ai_image_generation_per_month")
    private Integer aiImageGenerationPerMonth;

    /** 是否支持分享链接 */
    @Column(name = "has_share_link")
    private Boolean hasShareLink;

    /** 是否支持发布功能 */
    @Column(name = "has_publish")
    private Boolean hasPublish;

    /** 工作空间成员数量限制 */
    @Column(name = "workspace_member_limit")
    private Integer workspaceMemberLimit;

    /** 协作工作空间数量限制 */
    @Column(name = "collaborative_workspace_limit")
    private Integer collaborativeWorkspaceLimit;

    /** 页面权限-访客编辑人数 */
    @Column(name = "page_permission_guest_editors")
    private Integer pagePermissionGuestEditors;

    /** 是否支持空间成员管理 */
    @Column(name = "has_space_member_management")
    private Boolean hasSpaceMemberManagement;

    /** 是否支持空间成员分组 */
    @Column(name = "has_space_member_grouping")
    private Boolean hasSpaceMemberGrouping;

    /** 是否启用 */
    @Column(name = "is_active")
    private Boolean isActive;

    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
