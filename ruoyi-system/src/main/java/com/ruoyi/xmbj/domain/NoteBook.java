package com.ruoyi.xmbj.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class NoteBook {
//    quick_note_id
//            workspace_id
//    uid
//            updated_at
//    created_at
//            data
    /**
     * quick_note_id,oid
     */
    @Column(name = "oid")
    private String oid;

    @Column(name = "workspace_id")
    private String workspaceId;

    /**
     * uid
     */
    @Column(name = "owner_uid")
    private Long ownerUid;

    @Column(name = "partition_key")
    private Long partitionKey;

    @Column(name = "len")
    private Integer len;

    @Column(name = "blob")
    private byte[] blob;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "indexed_at")
    private Date indexedAt;

    @Column(name = "title")
    private String title;

    /**
     * afCollb,
     * quickNote
     */
    @Column(name = "notebook_type")
    private String notebookType;

    @Column(name = "data")
    private String data;

    private String content;
}
