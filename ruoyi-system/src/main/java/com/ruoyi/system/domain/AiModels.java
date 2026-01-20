package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AI模型管理对象 ai_models
 * 
 * @author ruoyi
 * @date 2026-01-25
 */
public class AiModels extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String name;

    /** 版本 */
    @Excel(name = "版本")
    private String version;

    /** API密钥 */
    @Excel(name = "API密钥")
    private String apiKey;

    /** API调用地址 */
    @Excel(name = "API调用地址")
    private String apiUrl;

    /** 状态（0-未发布 1-已发布 2-已停用） */
    @Excel(name = "状态", readConverterExp = "0=未发布,1=已发布,2=已停用")
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 操作人姓名 */
    @Excel(name = "操作人姓名")
    private String operatorName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    public void setApiKey(String apiKey) 
    {
        this.apiKey = apiKey;
    }

    public String getApiKey() 
    {
        return apiKey;
    }

    public void setApiUrl(String apiUrl) 
    {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() 
    {
        return apiUrl;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return this.operator;
    }

    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return this.operatorName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("version", getVersion())
            .append("apiKey", getApiKey())
            .append("apiUrl", getApiUrl())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("operator", getOperator())
            .toString();
    }
}
