package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_collab_member")
public class AfCollabMember {
    @Column(name = "uid")
    private Long uid;
    @Column(name = "uid")
    private String oid;
    @Column(name = "permission_id")
    private Integer  permissionId;
    @Column(name = "created_at")
    private Date createdAt;
}
