package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户附加服务表实体
 * @author ruoyi
 */
@Data
@Builder
@TableName("af_user_addons")
public class AfUserAddons implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long uid;

    /** 附加服务ID */
    private Long addonId;

    /** 附加服务数量 */
    private Integer quantity;

    /** 生效开始时间 */
    private LocalDateTime startDate;

    /** 生效到期时间 */
    private LocalDateTime endDate;

    /** 状态（如：active-生效/expired-过期/canceled-已取消） */
    private String status;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}
