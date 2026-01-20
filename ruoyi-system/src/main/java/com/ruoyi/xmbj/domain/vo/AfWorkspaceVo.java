package com.ruoyi.xmbj.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AfWorkspaceVo
{
    private String workspaceId;
    private String workspaceName;
    private String icon;
    private String workspaceType;
    private Long memberCount;
    private Long guestEditPermissionCount;
    private Date createdAt;
    private Date expiredAt;

    public void setWorkspaceId(String workspaceId) 
    {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceId() 
    {
        return workspaceId;
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

    public void setWorkspaceType(String workspaceType) 
    {
        this.workspaceType = workspaceType;
    }

    public String getWorkspaceType() 
    {
        return workspaceType;
    }

    public void setMemberCount(Long memberCount) 
    {
        this.memberCount = memberCount;
    }

    public Long getMemberCount() 
    {
        return memberCount;
    }

    public void setGuestEditPermissionCount(Long guestEditPermissionCount) 
    {
        this.guestEditPermissionCount = guestEditPermissionCount;
    }

    public Long getGuestEditPermissionCount() 
    {
        return guestEditPermissionCount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getExpiredAt() 
    {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) 
    {
        this.expiredAt = expiredAt;
    }
}
