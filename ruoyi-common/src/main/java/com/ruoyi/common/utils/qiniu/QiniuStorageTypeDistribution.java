package com.ruoyi.common.utils.qiniu;

import java.math.BigDecimal;

public class QiniuStorageTypeDistribution
{
    private String storageType;

    private Long capacityTB;

    private BigDecimal percentage;

    public QiniuStorageTypeDistribution()
    {
    }

    public QiniuStorageTypeDistribution(String storageType, Long capacityTB, BigDecimal percentage)
    {
        this.storageType = storageType;
        this.capacityTB = capacityTB;
        this.percentage = percentage;
    }

    public String getStorageType()
    {
        return storageType;
    }

    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }

    public Long getCapacityTB()
    {
        return capacityTB;
    }

    public void setCapacityTB(Long capacityTB)
    {
        this.capacityTB = capacityTB;
    }

    public BigDecimal getPercentage()
    {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage)
    {
        this.percentage = percentage;
    }
}
