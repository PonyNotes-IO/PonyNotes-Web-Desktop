package com.ruoyi.common.utils.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.ruoyi.common.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QiniuService
{
    @Autowired
    private QiniuConfig qiniuConfig;

    @Autowired
    private QiniuUtils qiniuUtils;

    public QiniuStorageInfo getStorageInfo() throws QiniuException
    {
        if (!qiniuConfig.isEnabled())
        {
            throw new RuntimeException("七牛云服务未启用");
        }

        QiniuStorageInfo info = new QiniuStorageInfo();
        long totalCount = 0;
        long totalSize = 0;

        String marker = null;
        String prefix = "";
        int limit = 1000;

        Configuration cfg = new Configuration(getRegion(qiniuConfig.getRegion()));
        BucketManager bucketManager = new BucketManager(
            com.qiniu.util.Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey()),
            cfg
        );

        do
        {
            FileListing fileListing = bucketManager.listFiles(qiniuConfig.getBucket(), prefix, marker, limit, null);
            if (fileListing == null || fileListing.items == null)
            {
                break;
            }

            FileInfo[] items = fileListing.items;
            if (items != null && items.length > 0)
            {
                for (FileInfo item : items)
                {
                    totalCount++;
                    totalSize += item.fsize;
                }
            }

            marker = fileListing.marker;
        }
        while (marker != null && !marker.isEmpty());

        info.setFileCount(totalCount);
        info.setSpaceSize(totalSize);
        info.setBucket(qiniuConfig.getBucket());
        info.setDomain(qiniuConfig.getDomain());

        long totalCapacity = qiniuConfig.getTotalCapacity() != null ? qiniuConfig.getTotalCapacity() : 16777216000000000L;
        long remainingCapacity = totalCapacity - totalSize;
        double usageRate = totalCapacity > 0 ? (double) totalSize / totalCapacity * 100 : 0.0;

        info.setTotalCapacity(totalCapacity);
        info.setRemainingCapacity(remainingCapacity);
        info.setUsageRate(Math.round(usageRate * 10.0) / 10.0);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        info.setLastUpdateTime(sdf.format(new java.util.Date()));

        return info;
    }

    public List<FileInfo> getFileList(String prefix, int limit) throws QiniuException
    {
        if (!qiniuConfig.isEnabled())
        {
            throw new RuntimeException("七牛云服务未启用");
        }

        Configuration cfg = new Configuration(getRegion(qiniuConfig.getRegion()));
        BucketManager bucketManager = new BucketManager(
            com.qiniu.util.Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey()),
            cfg
        );

        FileListing fileListing = bucketManager.listFiles(qiniuConfig.getBucket(), prefix, null, limit, null);
        
        List<FileInfo> fileList = new ArrayList<>();
        if (fileListing != null && fileListing.items != null)
        {
            for (FileInfo item : fileListing.items)
            {
                fileList.add(item);
            }
        }

        return fileList;
    }

    public void deleteFile(String key) throws QiniuException
    {
        if (!qiniuConfig.isEnabled())
        {
            throw new RuntimeException("七牛云服务未启用");
        }

        Configuration cfg = new Configuration(getRegion(qiniuConfig.getRegion()));
        BucketManager bucketManager = new BucketManager(
            com.qiniu.util.Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey()),
            cfg
        );

        bucketManager.delete(qiniuConfig.getBucket(), key);
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
}
