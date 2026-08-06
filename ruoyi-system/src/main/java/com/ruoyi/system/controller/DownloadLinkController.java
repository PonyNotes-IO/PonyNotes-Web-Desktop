package com.ruoyi.system.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.DownloadLink;
import com.ruoyi.system.service.IDownloadLinkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 下载链接配置Controller
 * 用于后台管理 xmbj-www-ui /download 页面各平台的下载链接
 *
 * @author 张继科
 * @date 2026-08-05
 */
@RestController
@RequestMapping("/xmbj/downloadLink")
public class DownloadLinkController extends BaseController
{
    @Autowired
    private IDownloadLinkService downloadLinkService;

    /**
     * 查询下载链接列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:downloadLink:list')")
    @GetMapping("/list")
    public TableDataInfo list(DownloadLink downloadLink)
    {
        startPage();
        List<DownloadLink> list = downloadLinkService.selectDownloadLinkList(downloadLink);
        return getDataTable(list);
    }

    /**
     * 公开接口：查询已启用的下载链接列表（供 xmbj-www-ui /download 页面调用，无需登录）
     */
    @Anonymous
    @GetMapping("/public/list")
    public AjaxResult publicList()
    {
        DownloadLink query = new DownloadLink();
        query.setStatus(1);
        List<DownloadLink> list = downloadLinkService.selectDownloadLinkList(query);
        return AjaxResult.success(list);
    }

    /**
     * 导出下载链接列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:downloadLink:export')")
    @Log(title = "下载链接配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DownloadLink downloadLink)
    {
        List<DownloadLink> list = downloadLinkService.selectDownloadLinkList(downloadLink);
        ExcelUtil<DownloadLink> util = new ExcelUtil<DownloadLink>(DownloadLink.class);
        util.exportExcel(response, list, "下载链接配置数据");
    }

    /**
     * 获取下载链接详细信息
     */
    @PreAuthorize("@ss.hasPermi('xmbj:downloadLink:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(downloadLinkService.selectDownloadLinkById(id));
    }

    /**
     * 新增下载链接
     */
    @PreAuthorize("@ss.hasPermi('xmbj:downloadLink:add')")
    @Log(title = "下载链接配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DownloadLink downloadLink)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        downloadLink.setOperator(String.valueOf(loginUser.getUserId()));
        downloadLink.setCreatedTime(new Date());
        downloadLink.setDelFlag(0);
        if (downloadLink.getSortOrder() == null) {
            downloadLink.setSortOrder(0);
        }
        if (downloadLink.getStatus() == null) {
            downloadLink.setStatus(1);
        }
        return toAjax(downloadLinkService.insertDownloadLink(downloadLink));
    }

    /**
     * 修改下载链接
     */
    @PreAuthorize("@ss.hasPermi('xmbj:downloadLink:edit')")
    @Log(title = "下载链接配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DownloadLink downloadLink)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        downloadLink.setOperator(String.valueOf(loginUser.getUserId()));
        return toAjax(downloadLinkService.updateDownloadLink(downloadLink));
    }

    /**
     * 删除下载链接
     */
    @PreAuthorize("@ss.hasPermi('xmbj:downloadLink:remove')")
    @Log(title = "下载链接配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(downloadLinkService.deleteDownloadLinkByIds(ids));
    }
}
