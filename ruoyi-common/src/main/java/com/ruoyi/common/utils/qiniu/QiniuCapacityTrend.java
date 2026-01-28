package com.ruoyi.common.utils.qiniu;

import java.math.BigDecimal;

public class QiniuCapacityTrend
{
    private String month;

    private Long capacityTB;

    private Long capacityBytes;

    private BigDecimal growthRate;

    private BigDecimal cumulativeGrowthRate;

    private Long monthGrowthTB;

    public QiniuCapacityTrend()
    {
    }

    public QiniuCapacityTrend(String month, Long capacityTB)
    {
        this.month = month;
        this.capacityTB = capacityTB;
        this.capacityBytes = capacityTB * 1024L * 1024 * 1024 * 1024;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public Long getCapacityTB()
    {
        return capacityTB;
    }

    public void setCapacityTB(Long capacityTB)
    {
        this.capacityTB = capacityTB;
        this.capacityBytes = capacityTB * 1024L * 1024 * 1024 * 1024;
    }

    public Long getCapacityBytes()
    {
        return capacityBytes;
    }

    public void setCapacityBytes(Long capacityBytes)
    {
        this.capacityBytes = capacityBytes;
    }

    public BigDecimal getGrowthRate()
    {
        return growthRate;
    }

    public void setGrowthRate(BigDecimal growthRate)
    {
        this.growthRate = growthRate;
    }

    public BigDecimal getCumulativeGrowthRate()
    {
        return cumulativeGrowthRate;
    }

    public void setCumulativeGrowthRate(BigDecimal cumulativeGrowthRate)
    {
        this.cumulativeGrowthRate = cumulativeGrowthRate;
    }

    public Long getMonthGrowthTB()
    {
        return monthGrowthTB;
    }

    public void setMonthGrowthTB(Long monthGrowthTB)
    {
        this.monthGrowthTB = monthGrowthTB;
    }
}
