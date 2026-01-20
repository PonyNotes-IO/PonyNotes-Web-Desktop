package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_workspace_member_profile")
public class AfWorkspaceMemberProfile {

    /** 用户ID */
    @Column(name = "uid")
    private Long uid;

    /** 关联工作空间ID */
    @Column(name = "workspace_id")
    private String workspaceId;

    /** 成员名称 */
    @Column(name = "name")
    private String name;

    /** 头像URL */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /** 封面图URL */
    @Column(name = "cover_image_url")
    private String coverImageUrl;

    /** 描述 */
    @Column(name = "description")
    private String description;

    /** 自定义图片URL */
    @Column(name = "custom_image_url")
    private String customImageUrl;
}
