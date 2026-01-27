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
@TableName("af_collab")
public class AfCollab {
    private static final long serialVersionUID = 1L;

    /** 工作区ID */
    @Id
    @Column(name = "oid")
    private String oid;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "owner_uid")
    private Long ownerUid;

    @Column(name = "partition_key")
    private Long partitionKey;

    @Column(name = "len")
    private Integer len;

    @Column(name = "blob")
    private String blob;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "indexed_at")
    private Date indexedAt;
}