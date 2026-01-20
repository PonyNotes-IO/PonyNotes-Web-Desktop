package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class AfUserWorkspace {
    /** 工作区ID */
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "owner_uid")
    private String ownerUid;

    @Column(name = "workspace_name")
    private String workspaceName;

    @Column(name = "member_count")
    private Integer memberCount;

    @Column(name = "member_ids")
    private Integer memberIds;

    @Column(name = "af_collab_count")
    private String afCollabCount;

    @Column(name = "af_collab_ids")
    private String afCollabIds;

    @Column(name = "af_invitation_count")
    private String afInvitationCount;

    @Column(name = "af_invitation_ids")
    private String afInvitationIds;

    @Column(name = "af_user_subscriptions_ids")
    private String afUserSubscriptionsIds;

    @Column(name = "start_date")
    private Date  startDate;

    @Column(name = "end_date")
    private Date  endDate;


}
