package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * 笔记信息表 sys_notes
 * 
 * @author ruoyi
 */
public class SysNotes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 笔记ID */
    private Long id;

    /** 笔记标题 */
    private String title;

    /** 笔记内容 */
    private String content;

    /** 创建者ID */
    private Long userId;

    /** 创建者名称 */
    private String userName;

    /** 分类ID */
    private Long categoryId;

    /** 标签（逗号分隔） */
    private String tags;

    /** 是否公开(0:私有,1:公开) */
    private Integer isPublic;

    /** 状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Xss(message = "笔记标题不能包含脚本字符")
    @NotBlank(message = "笔记标题不能为空")
    @Size(min = 0, max = 200, message = "笔记标题不能超过200个字符")
    public String getTitle()
    {
        return title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getTags()
    {
        return tags;
    }

    public void setIsPublic(Integer isPublic)
    {
        this.isPublic = isPublic;
    }

    public Integer getIsPublic()
    {
        return isPublic;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("categoryId", getCategoryId())
            .append("tags", getTags())
            .append("isPublic", getIsPublic())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}