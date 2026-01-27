package com.ruoyi.web.controller.ponynotes;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xmbj.domain.XmAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xmbj.service.AppVersionService;

import javax.servlet.http.HttpServletResponse;

/**
 * 应用版本信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/ponynotes/appVersion")
public class XmAppVersionController extends BaseController {
    @Autowired
    private AppVersionService appVersionService;

    /**
     * 获取应用版本列表
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:list')")
    @GetMapping("/list")
    public TableDataInfo list(XmAppVersion appVersion) {
        startPage();
        List<XmAppVersion> list = appVersionService.selectAppVersionList(appVersion);
        return getDataTable(list);
    }

    /**
     * 导出应用版本列表
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:export')")
    @Log(title = "应用版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, XmAppVersion appVersion) {
        List<XmAppVersion> list = appVersionService.selectAppVersionList(appVersion);
        ExcelUtil<XmAppVersion> util = new ExcelUtil<XmAppVersion>(XmAppVersion.class);
        util.exportExcel(response, list, "应用版本数据");
    }

    /**
     * 根据应用版本编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(appVersionService.selectAppVersionById(id));
    }

    /**
     * 新增应用版本
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:add')")
    @Log(title = "应用版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody XmAppVersion appVersion) {
        return toAjax(appVersionService.insertAppVersion(appVersion));
    }

    /**
     * 修改应用版本
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:edit')")
    @Log(title = "应用版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody XmAppVersion appVersion) {
        return toAjax(appVersionService.updateAppVersion(appVersion));
    }

    /**
     * 删除应用版本
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:remove')")
    @Log(title = "应用版本", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(appVersionService.deleteAppVersionByIds(ids));
    }

    /**
     * 停用应用版本
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:edit')")
    @Log(title = "应用版本", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/disable")
    public AjaxResult disable(@PathVariable Long id) {
        return toAjax(appVersionService.disableAppVersion(id));
    }

    /**
     * 启用应用版本
     */
    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:edit')")
    @Log(title = "应用版本", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/enable")
    public AjaxResult enable(@PathVariable Long id) {
        return toAjax(appVersionService.enableAppVersion(id));
    }

    /**
     * 查询所有活跃的应用版本
     */
    @GetMapping("/active")
    public AjaxResult getActive() {
        List<XmAppVersion> list = appVersionService.selectActiveAppVersions();
        return success(list);
    }
}