package com.ruoyi.common.utils.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.ruoyi.common.config.QiniuConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class QiniuUtils
{
    private static final Logger log = LoggerFactory.getLogger(QiniuUtils.class);

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
        log.info("七牛云上传成功: bucket={}, key={}, returnedKey={}, hash={}", 
                qiniuConfig.getBucket(), key, putRet.key, putRet.hash);
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

    public boolean fileExists(String key)
    {
        if (bucketManager == null)
        {
            init();
        }
        try
        {
            FileInfo fileInfo = bucketManager.stat(qiniuConfig.getBucket(), key);
            boolean exists = fileInfo != null;
            log.info("七牛云文件存在检查: bucket={}, key={}, exists={}, fsize={}", 
                    qiniuConfig.getBucket(), key, exists, fileInfo != null ? fileInfo.fsize : 0);
            return exists;
        }
        catch (QiniuException e)
        {
            if (e.code() == 612)
            {
                log.warn("七牛云文件不存在: bucket={}, key={}, code={}", qiniuConfig.getBucket(), key, e.code());
                return false;
            }
            log.error("七牛云检查文件是否存在异常: bucket={}, key={}, code={}, msg={}", 
                    qiniuConfig.getBucket(), key, e.code(), e.getMessage(), e);
            throw new RuntimeException("检查文件是否存在失败", e);
        }
    }

    public InputStream downloadFile(String key) throws IOException
    {
        String primaryUrl = getFileUrl(key);
        log.info("七牛云下载文件(主用-CDN): bucket={}, key={}, url={}", qiniuConfig.getBucket(), key, primaryUrl);
        try
        {
            InputStream is = tryDownload(primaryUrl);
            if (is != null)
            {
                return is;
            }
        }
        catch (IOException e)
        {
            log.warn("七牛云CDN域名下载失败，尝试备用源站域名: {}", e.getMessage());
        }

        String s3Endpoint = qiniuConfig.getS3Endpoint();
        if (s3Endpoint != null && !s3Endpoint.isEmpty())
        {
            String fallbackUrl = buildS3Url(s3Endpoint, qiniuConfig.getBucket(), key);
            log.info("七牛云下载文件(备用-S3源站): bucket={}, key={}, url={}", qiniuConfig.getBucket(), key, fallbackUrl);
            try
            {
                InputStream is = tryDownload(fallbackUrl);
                if (is != null)
                {
                    return is;
                }
            }
            catch (IOException e)
            {
                log.error("七牛云S3源站域名下载也失败: {}", e.getMessage());
            }
        }

        throw new IOException("下载文件失败，所有域名均返回404，key=" + key + "，请检查七牛云域名配置");
    }

    private InputStream tryDownload(String fileUrl) throws IOException
    {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            return conn.getInputStream();
        }
        else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND)
        {
            return null;
        }
        else
        {
            throw new IOException("HTTP状态码: " + responseCode + ", URL: " + fileUrl);
        }
    }

    private String buildS3Url(String s3Endpoint, String bucket, String key)
    {
        String endpoint = s3Endpoint.endsWith("/") ? s3Endpoint : s3Endpoint + "/";
        return endpoint + bucket + "/" + key;
    }

    public long getFileSize(String key)
    {
        if (bucketManager == null)
        {
            init();
        }
        try
        {
            FileInfo fileInfo = bucketManager.stat(qiniuConfig.getBucket(), key);
            return fileInfo != null ? fileInfo.fsize : 0;
        }
        catch (QiniuException e)
        {
            throw new RuntimeException("获取文件大小失败", e);
        }
    }
}
