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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.AppVersion;
import com.ruoyi.system.service.IAppVersionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * App版本管理Controller
 * 
 * @author 张继科
 * @date 2026-01-25
 */
@RestController
@RequestMapping("/xmbj/appVersion")
public class AppVersionController extends BaseController
{
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 查询App版本管理列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:appVersion:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppVersion appVersion)
    {
        startPage();
        List<AppVersion> list = appVersionService.selectAppVersionList(appVersion);
        return getDataTable(list);
    }

    /**
     * 导出App版本管理列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:appVersion:export')")
    @Log(title = "App版本管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppVersion appVersion)
    {
        List<AppVersion> list = appVersionService.selectAppVersionList(appVersion);
        ExcelUtil<AppVersion> util = new ExcelUtil<AppVersion>(AppVersion.class);
        util.exportExcel(response, list, "App版本管理数据");
    }

    /**
     * 获取App版本管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('xmbj:appVersion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(appVersionService.selectAppVersionById(id));
    }

    /**
     * 新增App版本管理
     */
    @PreAuthorize("@ss.hasPermi('xmbj:appVersion:add')")
    @Log(title = "App版本管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppVersion appVersion)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        System.out.println("新增App版本管理，操作人ID：" + loginUser.getUserId());
        System.out.println("新增App版本管理，接收到的数据：" + appVersion.toString());
        appVersion.setOperator(String.valueOf(loginUser.getUserId()));
        appVersion.setCreatedTime(new Date());
        appVersion.setDelFlag(0);
        System.out.println("新增App版本管理，设置后的数据：" + appVersion.toString());
        return toAjax(appVersionService.insertAppVersion(appVersion));
    }

    /**
     * 修改App版本管理
     */
    @PreAuthorize("@ss.hasPermi('xmbj:appVersion:edit')")
    @Log(title = "App版本管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppVersion appVersion)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        System.out.println("修改App版本管理，操作人ID：" + loginUser.getUserId());
        appVersion.setOperator(String.valueOf(loginUser.getUserId()));
        return toAjax(appVersionService.updateAppVersion(appVersion));
    }

    /**
     * 删除App版本管理
     */
    @PreAuthorize("@ss.hasPermi('xmbj:appVersion:remove')")
    @Log(title = "App版本管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(appVersionService.deleteAppVersionByIds(ids));
    }
}
