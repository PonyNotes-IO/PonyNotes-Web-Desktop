package com.ruoyi.xmbj.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class AfSubscriptionAddons extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "补充包代码")
    private String addonCode;

    @Excel(name = "补充包名称")
    private String addonName;

    @Excel(name = "补充包中文名称")
    private String addonNameCn;

    @Excel(name = "补充包类型")
    private String addonType;

    @Excel(name = "价格（元）")
    private BigDecimal priceYuan;

    @Excel(name = "存储空间（GB）")
    private Integer storageGb;

    @Excel(name = "AI对话次数")
    private Integer aiChatCount;

    @Excel(name = "AI图片生成次数")
    private Integer aiImageCount;

    @Excel(name = "是否激活")
    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setAddonCode(String addonCode) 
    {
        this.addonCode = addonCode;
    }

    public String getAddonCode() 
    {
        return addonCode;
    }

    public void setAddonName(String addonName) 
    {
        this.addonName = addonName;
    }

    public String getAddonName() 
    {
        return addonName;
    }

    public void setAddonNameCn(String addonNameCn) 
    {
        this.addonNameCn = addonNameCn;
    }

    public String getAddonNameCn() 
    {
        return addonNameCn;
    }

    public void setAddonType(String addonType) 
    {
        this.addonType = addonType;
    }

    public String getAddonType() 
    {
        return addonType;
    }

    public void setPriceYuan(BigDecimal priceYuan) 
    {
        this.priceYuan = priceYuan;
    }

    public BigDecimal getPriceYuan() 
    {
        return priceYuan;
    }

    public void setStorageGb(Integer storageGb) 
    {
        this.storageGb = storageGb;
    }

    public Integer getStorageGb() 
    {
        return storageGb;
    }

    public void setAiChatCount(Integer aiChatCount) 
    {
        this.aiChatCount = aiChatCount;
    }

    public Integer getAiChatCount() 
    {
        return aiChatCount;
    }

    public void setAiImageCount(Integer aiImageCount) 
    {
        this.aiImageCount = aiImageCount;
    }

    public Integer getAiImageCount() 
    {
        return aiImageCount;
    }

    public void setIsActive(Boolean isActive) 
    {
        this.isActive = isActive;
    }

    public Boolean getIsActive() 
    {
        return isActive;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("addonCode", getAddonCode())
            .append("addonName", getAddonName())
            .append("addonNameCn", getAddonNameCn())
            .append("addonType", getAddonType())
            .append("priceYuan", getPriceYuan())
            .append("storageGb", getStorageGb())
            .append("aiChatCount", getAiChatCount())
            .append("aiImageCount", getAiImageCount())
            .append("isActive", getIsActive())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}