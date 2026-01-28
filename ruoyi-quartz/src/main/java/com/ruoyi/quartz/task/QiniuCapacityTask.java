package com.ruoyi.quartz.task;

import com.qiniu.common.QiniuException;
import com.ruoyi.common.utils.qiniu.QiniuService;
import com.ruoyi.common.utils.qiniu.QiniuStorageInfo;
import com.ruoyi.system.service.IQiniuCapacityHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("qiniuCapacityTask")
public class QiniuCapacityTask
{
    private static final Logger log = LoggerFactory.getLogger(QiniuCapacityTask.class);

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private IQiniuCapacityHistoryService capacityHistoryService;

    public void recordCapacity()
    {
        try
        {
            log.info("开始记录七牛云容量数据");

            QiniuStorageInfo storageInfo = qiniuService.getStorageInfo();

            if (storageInfo != null)
            {
                boolean success = capacityHistoryService.recordCapacityHistory(
                    storageInfo.getSpaceSize(),
                    storageInfo.getFileCount(),
                    storageInfo.getUsageRate()
                );

                if (success)
                {
                    log.info("七牛云容量数据记录成功：{} TB", 
                        String.format("%.2f", storageInfo.getSpaceSize() / (1024.0 * 1024 * 1024 * 1024)));
                }
                else
                {
                    log.error("七牛云容量数据记录失败");
                }
            }
            else
            {
                log.error("获取七牛云存储信息失败");
            }
        }
        catch (QiniuException e)
        {
            log.error("记录七牛云容量数据异常", e);
        }
        catch (Exception e)
        {
            log.error("记录七牛云容量数据异常", e);
        }
    }
}
