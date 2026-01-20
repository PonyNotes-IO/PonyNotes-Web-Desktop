package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_workspace_invitation")
public class AfWorkspaceInvitation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 工作区ID */
    @Id
    @Column(name = "id")
    private String  id;

    @Column(name = "workspace_id")
    private String  workspaceId;

    @Column(name = "inviter")
    private Long  inviter;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "invitee_email")
    private Date inviteeEmail;

}
