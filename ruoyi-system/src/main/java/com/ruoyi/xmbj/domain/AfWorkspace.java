package com.ruoyi.xmbj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.Date;

/**
 * 工作区信息对象 af_workspace
 *
 * @author ruoyi
 * @date 2025-12-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_workspace")
public class AfWorkspace extends BaseEntity {

    /** 工作区ID */
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "database_storage_id")
    private String databaseStorageId;

    @Column(name = "owner_uid")
    private String ownerUid;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    /** 工作区类型 0=免费版，1=基础版，2=专业版，3=团队版 */
    @Column(name = "workspace_type")
    private Integer workspaceType;

    @Column(name = "deleted_at")
    private Integer deletedAt;

    /** 发布命名空间 */
    @Column(name = "workspace_name")
    private String workspaceName;

    /** 工作区配置 */
    @Column(name = "icon")
    private String icon; // JSON字符串存储

    /** 工作区配置 */
    @Column(name = "settings")
    private String settings; // JSON字符串存储

    /** 是否初始化 */
    @Column(name = "is_initialized")
    private Boolean isInitialized;

    @Column(name = "default_published_view_id")
    private String default_published_view_id;
}
