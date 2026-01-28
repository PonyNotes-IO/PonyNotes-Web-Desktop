package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作空间表 sys_workspace
 * 
 * @author ruoyi
 */
public class SysWorkspace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工作空间ID */
    private String workspaceId;

    /** 数据库存储ID */
    private String databaseStorageId;

    /** 工作空间所有者用户ID */
    private Long ownerUid;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /** 工作空间类型（0：个人空间 1：团队空间） */
    private Integer workspaceType;

    /** 软删除时间（NULL表示未删除） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    /** 工作空间名称 */
    private String workspaceName;

    /** 工作空间图标（存储图标URL或编码） */
    private String icon;

    /** 工作空间配置（JSON格式，如主题、权限等） */
    private String settings;

    /** 是否已初始化（0：未初始化 1：已初始化） */
    private Integer isInitialized;

    /** 默认发布视图ID */
    private String defaultPublishedViewId;

    // getter and setter methods

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getDatabaseStorageId() {
        return databaseStorageId;
    }

    public void setDatabaseStorageId(String databaseStorageId) {
        this.databaseStorageId = databaseStorageId;
    }

    public Long getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(Long ownerUid) {
        this.ownerUid = ownerUid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getWorkspaceType() {
        return workspaceType;
    }

    public void setWorkspaceType(Integer workspaceType) {
        this.workspaceType = workspaceType;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public Integer getIsInitialized() {
        return isInitialized;
    }

    public void setIsInitialized(Integer isInitialized) {
        this.isInitialized = isInitialized;
    }

    public String getDefaultPublishedViewId() {
        return defaultPublishedViewId;
    }

    public void setDefaultPublishedViewId(String defaultPublishedViewId) {
        this.defaultPublishedViewId = defaultPublishedViewId;
    }
}