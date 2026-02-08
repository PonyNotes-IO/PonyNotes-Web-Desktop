package com.ruoyi.web.controller.system;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.model.FileInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.qiniu.QiniuCapacityAnalysis;
import com.ruoyi.common.utils.qiniu.QiniuService;
import com.ruoyi.common.utils.qiniu.QiniuStorageInfo;
import com.ruoyi.system.utils.qiniu.QiniuCapacityAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/qiniu")
public class QiniuController extends BaseController
{
    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private QiniuCapacityAnalyzer capacityAnalyzer;

    @GetMapping("/storage/info")
    @PreAuthorize("@ss.hasPermi('system:qiniu:query')")
    public AjaxResult getStorageInfo()
    {
        try
        {
            QiniuStorageInfo info = qiniuService.getStorageInfo();
            return AjaxResult.success(info);
        }
        catch (QiniuException e)
        {
            logger.error("获取七牛云存储信息失败", e);
            return AjaxResult.error("获取七牛云存储信息失败：" + e.getMessage());
        }
        catch (Exception e)
        {
            logger.error("获取七牛云存储信息失败", e);
            return AjaxResult.error("获取七牛云存储信息失败：" + e.getMessage());
        }
    }

    @GetMapping("/file/list")
    @PreAuthorize("@ss.hasPermi('system:qiniu:query')")
    public TableDataInfo getFileList(@RequestParam(required = false) String prefix, 
                               @RequestParam(defaultValue = "100") int limit)
    {
        try
        {
            List<FileInfo> fileList = qiniuService.getFileList(prefix, limit);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(200);
            rspData.setMsg("查询成功");
            rspData.setRows(fileList);
            rspData.setTotal(fileList.size());
            return rspData;
        }
        catch (QiniuException e)
        {
            logger.error("获取七牛云文件列表失败", e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(500);
            rspData.setMsg("获取七牛云文件列表失败：" + e.getMessage());
            return rspData;
        }
        catch (Exception e)
        {
            logger.error("获取七牛云文件列表失败", e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(500);
            rspData.setMsg("获取七牛云文件列表失败：" + e.getMessage());
            return rspData;
        }
    }

    @DeleteMapping("/file/delete")
    @PreAuthorize("@ss.hasPermi('system:qiniu:remove')")
    @Log(title = "七牛云文件", businessType = BusinessType.DELETE)
    public AjaxResult deleteFile(@RequestParam String key)
    {
        try
        {
            qiniuService.deleteFile(key);
            return AjaxResult.success();
        }
        catch (QiniuException e)
        {
            logger.error("删除七牛云文件失败", e);
            return AjaxResult.error("删除七牛云文件失败：" + e.getMessage());
        }
        catch (Exception e)
        {
            logger.error("删除七牛云文件失败", e);
            return AjaxResult.error("删除七牛云文件失败：" + e.getMessage());
        }
    }

    @GetMapping("/capacity/trend")
    @PreAuthorize("@ss.hasPermi('system:qiniu:query')")
    public AjaxResult getCapacityTrend()
    {
        try
        {
            QiniuCapacityAnalysis analysis = capacityAnalyzer.analyzeFromDatabase(6);
            return AjaxResult.success(analysis);
        }
        catch (Exception e)
        {
            logger.error("获取七牛云容量趋势分析失败", e);
            return AjaxResult.error("获取七牛云容量趋势分析失败：" + e.getMessage());
        }
    }
}
