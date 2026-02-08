package com.ruoyi.system.controller;

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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysUserThirdBind;
import com.ruoyi.system.service.ISysUserThirdBindService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 第三方登录绑定Controller
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/system/bind")
public class SysUserThirdBindController extends BaseController
{
    @Autowired
    private ISysUserThirdBindService sysUserThirdBindService;

    /**
     * 查询第三方登录绑定列表
     */
    @PreAuthorize("@ss.hasPermi('system:bind:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserThirdBind sysUserThirdBind)
    {
        startPage();
        List<SysUserThirdBind> list = sysUserThirdBindService.selectSysUserThirdBindList(sysUserThirdBind);
        return getDataTable(list);
    }

    /**
     * 导出第三方登录绑定列表
     */
    @PreAuthorize("@ss.hasPermi('system:bind:export')")
    @Log(title = "第三方登录绑定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserThirdBind sysUserThirdBind)
    {
        List<SysUserThirdBind> list = sysUserThirdBindService.selectSysUserThirdBindList(sysUserThirdBind);
        ExcelUtil<SysUserThirdBind> util = new ExcelUtil<SysUserThirdBind>(SysUserThirdBind.class);
        util.exportExcel(response, list, "第三方登录绑定数据");
    }

    /**
     * 获取第三方登录绑定详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bind:query')")
    @GetMapping(value = "/{bindId}")
    public AjaxResult getInfo(@PathVariable("bindId") Long bindId)
    {
        return success(sysUserThirdBindService.selectSysUserThirdBindByBindId(bindId));
    }

    /**
     * 新增第三方登录绑定
     */
    @PreAuthorize("@ss.hasPermi('system:bind:add')")
    @Log(title = "第三方登录绑定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserThirdBind sysUserThirdBind)
    {
        return toAjax(sysUserThirdBindService.insertSysUserThirdBind(sysUserThirdBind));
    }

    /**
     * 修改第三方登录绑定
     */
    @PreAuthorize("@ss.hasPermi('system:bind:edit')")
    @Log(title = "第三方登录绑定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserThirdBind sysUserThirdBind)
    {
        return toAjax(sysUserThirdBindService.updateSysUserThirdBind(sysUserThirdBind));
    }

    /**
     * 删除第三方登录绑定
     */
    @PreAuthorize("@ss.hasPermi('system:bind:remove')")
    @Log(title = "第三方登录绑定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bindIds}")
    public AjaxResult remove(@PathVariable Long[] bindIds)
    {
        return toAjax(sysUserThirdBindService.deleteSysUserThirdBindByBindIds(bindIds));
    }
}
