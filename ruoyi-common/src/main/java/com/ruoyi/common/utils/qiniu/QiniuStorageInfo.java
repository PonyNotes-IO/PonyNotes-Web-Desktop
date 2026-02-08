package com.ruoyi.common.utils.qiniu;

public class QiniuStorageInfo
{
    private Long fileCount;

    private Long spaceSize;

    private String bucket;

    private String domain;

    private Long totalCapacity;

    private Long remainingCapacity;

    private Double usageRate;

    private String lastUpdateTime;

    public Long getFileCount()
    {
        return fileCount;
    }

    public void setFileCount(Long fileCount)
    {
        this.fileCount = fileCount;
    }

    public Long getSpaceSize()
    {
        return spaceSize;
    }

    public void setSpaceSize(Long spaceSize)
    {
        this.spaceSize = spaceSize;
    }

    public String getBucket()
    {
        return bucket;
    }

    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public Long getTotalCapacity()
    {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity)
    {
        this.totalCapacity = totalCapacity;
    }

    public Long getRemainingCapacity()
    {
        return remainingCapacity;
    }

    public void setRemainingCapacity(Long remainingCapacity)
    {
        this.remainingCapacity = remainingCapacity;
    }

    public Double getUsageRate()
    {
        return usageRate;
    }

    public void setUsageRate(Double usageRate)
    {
        this.usageRate = usageRate;
    }

    public String getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }
}
