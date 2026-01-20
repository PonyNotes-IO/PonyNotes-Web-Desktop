package com.ruoyi.xmbj.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class AfWorkspace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String workspaceId;

    @Excel(name = "数据库存储ID")
    private String databaseStorageId;

    @Excel(name = "工作空间所有者用户ID")
    private Long ownerUid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Excel(name = "工作空间类型", readConverterExp = "0=个人空间,1=团队空间")
    private Integer workspaceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "软删除时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    @Excel(name = "工作空间名称")
    private String workspaceName;

    @Excel(name = "工作空间图标")
    private String icon;

    @Excel(name = "工作空间配置")
    private String settings;

    @Excel(name = "是否已初始化", readConverterExp = "0=未初始化,1=已初始化")
    private Integer isInitialized;

    @Excel(name = "默认发布视图ID")
    private String defaultPublishedViewId;

    @Excel(name = "工作空间所有者")
    private String ownerName;

    public void setWorkspaceId(String workspaceId) 
    {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceId() 
    {
        return workspaceId;
    }

    public void setDatabaseStorageId(String databaseStorageId) 
    {
        this.databaseStorageId = databaseStorageId;
    }

    public String getDatabaseStorageId() 
    {
        return databaseStorageId;
    }

    public void setOwnerUid(Long ownerUid) 
    {
        this.ownerUid = ownerUid;
    }

    public Long getOwnerUid() 
    {
        return ownerUid;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setWorkspaceType(Integer workspaceType) 
    {
        this.workspaceType = workspaceType;
    }

    public Integer getWorkspaceType() 
    {
        return workspaceType;
    }

    public void setDeletedAt(Date deletedAt) 
    {
        this.deletedAt = deletedAt;
    }

    public Date getDeletedAt() 
    {
        return deletedAt;
    }

    public void setWorkspaceName(String workspaceName) 
    {
        this.workspaceName = workspaceName;
    }

    public String getWorkspaceName() 
    {
        return workspaceName;
    }

    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }

    public void setSettings(String settings) 
    {
        this.settings = settings;
    }

    public String getSettings() 
    {
        return settings;
    }

    public void setIsInitialized(Integer isInitialized) 
    {
        this.isInitialized = isInitialized;
    }

    public Integer getIsInitialized() 
    {
        return isInitialized;
    }

    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    public void setDefaultPublishedViewId(String defaultPublishedViewId) 
    {
        this.defaultPublishedViewId = defaultPublishedViewId;
    }

    public String getDefaultPublishedViewId() 
    {
        return defaultPublishedViewId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workspaceId", getWorkspaceId())
            .append("databaseStorageId", getDatabaseStorageId())
            .append("ownerUid", getOwnerUid())
            .append("createdAt", getCreatedAt())
            .append("workspaceType", getWorkspaceType())
            .append("deletedAt", getDeletedAt())
            .append("workspaceName", getWorkspaceName())
            .append("icon", getIcon())
            .append("settings", getSettings())
            .append("isInitialized", getIsInitialized())
            .append("defaultPublishedViewId", getDefaultPublishedViewId())
            .append("ownerName", getOwnerName())
            .toString();
    }
}
