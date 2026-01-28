package com.ruoyi.common.utils.qiniu;

import java.math.BigDecimal;
import java.util.List;

public class QiniuCapacityAnalysis
{
    private List<QiniuCapacityTrend> trendList;

    private String period;

    private Long startCapacityTB;

    private Long endCapacityTB;

    private Long totalGrowthTB;

    private BigDecimal totalGrowthRate;

    private BigDecimal averageCapacityTB;

    private BigDecimal averageMonthlyGrowthTB;

    private String trendDescription;

    private String peakMonth;

    private Long peakCapacityTB;

    private String fastestGrowthMonth;

    private BigDecimal fastestGrowthRate;

    private List<QiniuStorageTypeDistribution> storageTypeDistribution;

    private Long totalCapacityTB;

    public List<QiniuCapacityTrend> getTrendList()
    {
        return trendList;
    }

    public void setTrendList(List<QiniuCapacityTrend> trendList)
    {
        this.trendList = trendList;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public Long getStartCapacityTB()
    {
        return startCapacityTB;
    }

    public void setStartCapacityTB(Long startCapacityTB)
    {
        this.startCapacityTB = startCapacityTB;
    }

    public Long getEndCapacityTB()
    {
        return endCapacityTB;
    }

    public void setEndCapacityTB(Long endCapacityTB)
    {
        this.endCapacityTB = endCapacityTB;
    }

    public Long getTotalGrowthTB()
    {
        return totalGrowthTB;
    }

    public void setTotalGrowthTB(Long totalGrowthTB)
    {
        this.totalGrowthTB = totalGrowthTB;
    }

    public BigDecimal getTotalGrowthRate()
    {
        return totalGrowthRate;
    }

    public void setTotalGrowthRate(BigDecimal totalGrowthRate)
    {
        this.totalGrowthRate = totalGrowthRate;
    }

    public BigDecimal getAverageCapacityTB()
    {
        return averageCapacityTB;
    }

    public void setAverageCapacityTB(BigDecimal averageCapacityTB)
    {
        this.averageCapacityTB = averageCapacityTB;
    }

    public BigDecimal getAverageMonthlyGrowthTB()
    {
        return averageMonthlyGrowthTB;
    }

    public void setAverageMonthlyGrowthTB(BigDecimal averageMonthlyGrowthTB)
    {
        this.averageMonthlyGrowthTB = averageMonthlyGrowthTB;
    }

    public String getTrendDescription()
    {
        return trendDescription;
    }

    public void setTrendDescription(String trendDescription)
    {
        this.trendDescription = trendDescription;
    }

    public String getPeakMonth()
    {
        return peakMonth;
    }

    public void setPeakMonth(String peakMonth)
    {
        this.peakMonth = peakMonth;
    }

    public Long getPeakCapacityTB()
    {
        return peakCapacityTB;
    }

    public void setPeakCapacityTB(Long peakCapacityTB)
    {
        this.peakCapacityTB = peakCapacityTB;
    }

    public String getFastestGrowthMonth()
    {
        return fastestGrowthMonth;
    }

    public void setFastestGrowthMonth(String fastestGrowthMonth)
    {
        this.fastestGrowthMonth = fastestGrowthMonth;
    }

    public BigDecimal getFastestGrowthRate()
    {
        return fastestGrowthRate;
    }

    public void setFastestGrowthRate(BigDecimal fastestGrowthRate)
    {
        this.fastestGrowthRate = fastestGrowthRate;
    }

    public List<QiniuStorageTypeDistribution> getStorageTypeDistribution()
    {
        return storageTypeDistribution;
    }

    public void setStorageTypeDistribution(List<QiniuStorageTypeDistribution> storageTypeDistribution)
    {
        this.storageTypeDistribution = storageTypeDistribution;
    }

    public Long getTotalCapacityTB()
    {
        return totalCapacityTB;
    }

    public void setTotalCapacityTB(Long totalCapacityTB)
    {
        this.totalCapacityTB = totalCapacityTB;
    }
}
