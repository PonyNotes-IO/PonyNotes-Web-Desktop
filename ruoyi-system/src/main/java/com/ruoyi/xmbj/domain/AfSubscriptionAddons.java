package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_subscription_addons")
public class AfSubscriptionAddons extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /** 附加服务编码 */
    @Column(name = "addon_code")
    private String addonCode;

    /** 附加服务名称（英文） */
    @Column(name = "addon_name")
    private String addonName;

    /** 附加服务名称（中文） */
    @Column(name = "addon_name_cn")
    private String addonNameCn;

    /** 附加服务类型 */
    @Column(name = "addon_type")
    private String addonType;

    /** 价格（元） */
    @Column(name = "price_yuan")
    private BigDecimal priceYuan;

    /** 存储容量（GB） */
    @Column(name = "storage_gb")
    private Integer storageGb;

    /** AI聊天次数 */
    @Column(name = "ai_chat_count")
    private Integer aiChatCount;

    /** AI生成图片次数 */
    @Column(name = "ai_image_count")
    private Integer aiImageCount;

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