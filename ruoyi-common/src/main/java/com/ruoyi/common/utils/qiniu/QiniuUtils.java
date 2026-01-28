package com.ruoyi.common.utils.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ruoyi.common.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class QiniuUtils
{
    @Autowired
    private QiniuConfig qiniuConfig;

    private Auth auth;

    private UploadManager uploadManager;

    private BucketManager bucketManager;

    public void init()
    {
        auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        
        Configuration cfg = new Configuration(getRegion(qiniuConfig.getRegion()));
        uploadManager = new UploadManager(cfg);
        bucketManager = new BucketManager(auth, cfg);
    }

    private Region getRegion(String region)
    {
        switch (region)
        {
            case "z0":
                return Region.huadong();
            case "z1":
                return Region.huabei();
            case "z2":
                return Region.huanan();
            case "na0":
                return Region.beimei();
            case "as0":
                return Region.xinjiapo();
            default:
                return Region.autoRegion();
        }
    }

    public String getUpToken()
    {
        if (auth == null)
        {
            init();
        }
        return auth.uploadToken(qiniuConfig.getBucket());
    }

    public String uploadFile(File file, String key) throws QiniuException
    {
        if (uploadManager == null)
        {
            init();
        }
        Response response = uploadManager.put(file, key, getUpToken());
        DefaultPutRet putRet = response.jsonToObject(DefaultPutRet.class);
        return putRet.key;
    }

    public String uploadFile(InputStream inputStream, String key) throws QiniuException
    {
        if (uploadManager == null)
        {
            init();
        }
        Response response = uploadManager.put(inputStream, key, getUpToken(), null, null);
        DefaultPutRet putRet = response.jsonToObject(DefaultPutRet.class);
        return putRet.key;
    }

    public String uploadFile(byte[] data, String key) throws QiniuException
    {
        if (uploadManager == null)
        {
            init();
        }
        Response response = uploadManager.put(data, key, getUpToken());
        DefaultPutRet putRet = response.jsonToObject(DefaultPutRet.class);
        return putRet.key;
    }

    public void deleteFile(String key) throws QiniuException
    {
        if (bucketManager == null)
        {
            init();
        }
        bucketManager.delete(qiniuConfig.getBucket(), key);
    }

    public String getPrivateUrl(String key)
    {
        if (auth == null)
        {
            init();
        }
        long expireInSeconds = qiniuConfig.getUrlExpireSeconds();
        return auth.privateDownloadUrl(getPublicUrl(key), expireInSeconds);
    }

    public String getPublicUrl(String key)
    {
        String protocol = qiniuConfig.isUseHttps() ? "https" : "http";
        return protocol + "://" + qiniuConfig.getDomain() + "/" + key;
    }

    public String getFileUrl(String key)
    {
        if (qiniuConfig.isBucketPrivate())
        {
            return getPrivateUrl(key);
        }
        else
        {
            return getPublicUrl(key);
        }
    }
}
