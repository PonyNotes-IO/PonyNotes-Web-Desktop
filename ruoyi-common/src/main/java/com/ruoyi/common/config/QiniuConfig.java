package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云配置
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuConfig
{
    private boolean enabled;

    private String domain;

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String region;

    private String s3Endpoint;

    private boolean bucketPrivate;

    private int urlExpireSeconds;

    private boolean useHttps;

    private Long totalCapacity;

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public String getAccessKey()
    {
        return accessKey;
    }

    public void setAccessKey(String accessKey)
    {
        this.accessKey = accessKey;
    }

    public String getSecretKey()
    {
        return secretKey;
    }

    public void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    public String getBucket()
    {
        return bucket;
    }

    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getS3Endpoint()
    {
        return s3Endpoint;
    }

    public void setS3Endpoint(String s3Endpoint)
    {
        this.s3Endpoint = s3Endpoint;
    }

    public boolean isBucketPrivate()
    {
        return bucketPrivate;
    }

    public void setBucketPrivate(boolean bucketPrivate)
    {
        this.bucketPrivate = bucketPrivate;
    }

    public int getUrlExpireSeconds()
    {
        return urlExpireSeconds;
    }

    public void setUrlExpireSeconds(int urlExpireSeconds)
    {
        this.urlExpireSeconds = urlExpireSeconds;
    }

    public boolean isUseHttps()
    {
        return useHttps;
    }

    public void setUseHttps(boolean useHttps)
    {
        this.useHttps = useHttps;
    }

    public Long getTotalCapacity()
    {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity)
    {
        this.totalCapacity = totalCapacity;
    }
}
