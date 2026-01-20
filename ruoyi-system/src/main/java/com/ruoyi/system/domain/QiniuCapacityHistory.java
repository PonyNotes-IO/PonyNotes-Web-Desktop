package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class QiniuCapacityHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long historyId;

    private String recordDate;

    private Long capacityBytes;

    private BigDecimal capacityTb;

    private Long fileCount;

    private BigDecimal usageRate;

    public Long getHistoryId()
    {
        return historyId;
    }

    public void setHistoryId(Long historyId)
    {
        this.historyId = historyId;
    }

    public String getRecordDate()
    {
        return recordDate;
    }

    public void setRecordDate(String recordDate)
    {
        this.recordDate = recordDate;
    }

    public Long getCapacityBytes()
    {
        return capacityBytes;
    }

    public void setCapacityBytes(Long capacityBytes)
    {
        this.capacityBytes = capacityBytes;
    }

    public BigDecimal getCapacityTb()
    {
        return capacityTb;
    }

    public void setCapacityTb(BigDecimal capacityTb)
    {
        this.capacityTb = capacityTb;
    }

    public Long getFileCount()
    {
        return fileCount;
    }

    public void setFileCount(Long fileCount)
    {
        this.fileCount = fileCount;
    }

    public BigDecimal getUsageRate()
    {
        return usageRate;
    }

    public void setUsageRate(BigDecimal usageRate)
    {
        this.usageRate = usageRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("historyId", getHistoryId())
            .append("recordDate", getRecordDate())
            .append("capacityBytes", getCapacityBytes())
            .append("capacityTb", getCapacityTb())
            .append("fileCount", getFileCount())
            .append("usageRate", getUsageRate())
            .toString();
    }
}
