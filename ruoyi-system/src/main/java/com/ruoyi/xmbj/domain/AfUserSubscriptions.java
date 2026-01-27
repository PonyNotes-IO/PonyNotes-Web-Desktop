package com.ruoyi.xmbj.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;

/**
 * 用户订阅信息对象 af_user_subscriptions
 *
 * @author ruoyi
 * @date 2025-12-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_user_subscriptions")
public class AfUserSubscriptions extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Long id;

    /** 用户ID */
    @Column(name = "uid")
    private Long uid;

    /** 套餐ID */
    @Column(name = "plan_id")
    private Long planId;

    /** 计费类型 */
    @Column(name = "billing_type")
    private String billingType;

    /** 订阅状态 */
    @Column(name = "status")
    private String status;

    /** 订阅开始时间 */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /** 订阅到期时间 */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /** 取消时间 */
    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** 取消原因 */
    @Column(name = "cancel_reason")
    private String cancelReason;
}