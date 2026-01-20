package com.ruoyi.xmbj.domain;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 工作区成员实体
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "af_workspace_member")
public class AfWorkspaceMember extends BaseEntity implements Serializable {

    /** 用户ID */
    @Column(name = "uid")
    private Long uid;

    /** 工作区ID */
    @Column(name = "workspace_id")
    private Long workspaceId;

    /** 角色：owner=所有者，admin=管理员，member=普通成员，guest=访客 */
    @Column(name = "role_id")
    private Long roleId;

    /** 加入时间 */
    @Column(name = "created_at")
    private Date createdAt;

    /** 退出时间 */
    @Column(name = "updated_at")
    private Date updatedAt;
}
