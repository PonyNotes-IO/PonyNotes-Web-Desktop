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
import com.ruoyi.system.domain.SysUserLoginProfile;
import com.ruoyi.system.service.ISysUserLoginProfileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户登录概况Controller
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/system/profile")
public class SysUserLoginProfileController extends BaseController
{
    @Autowired
    private ISysUserLoginProfileService sysUserLoginProfileService;

    /**
     * 查询用户登录概况列表
     */
    @PreAuthorize("@ss.hasPermi('system:profile:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserLoginProfile sysUserLoginProfile)
    {
        startPage();
        List<SysUserLoginProfile> list = sysUserLoginProfileService.selectSysUserLoginProfileList(sysUserLoginProfile);
        return getDataTable(list);
    }

    /**
     * 导出用户登录概况列表
     */
    @PreAuthorize("@ss.hasPermi('system:profile:export')")
    @Log(title = "用户登录概况", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserLoginProfile sysUserLoginProfile)
    {
        List<SysUserLoginProfile> list = sysUserLoginProfileService.selectSysUserLoginProfileList(sysUserLoginProfile);
        ExcelUtil<SysUserLoginProfile> util = new ExcelUtil<SysUserLoginProfile>(SysUserLoginProfile.class);
        util.exportExcel(response, list, "用户登录概况数据");
    }

    /**
     * 获取用户登录概况详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:profile:query')")
    @GetMapping(value = "/{profileId}")
    public AjaxResult getInfo(@PathVariable("profileId") Long profileId)
    {
        return success(sysUserLoginProfileService.selectSysUserLoginProfileByProfileId(profileId));
    }

    /**
     * 新增用户登录概况
     */
    @PreAuthorize("@ss.hasPermi('system:profile:add')")
    @Log(title = "用户登录概况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserLoginProfile sysUserLoginProfile)
    {
        return toAjax(sysUserLoginProfileService.insertSysUserLoginProfile(sysUserLoginProfile));
    }

    /**
     * 修改用户登录概况
     */
    @PreAuthorize("@ss.hasPermi('system:profile:edit')")
    @Log(title = "用户登录概况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserLoginProfile sysUserLoginProfile)
    {
        return toAjax(sysUserLoginProfileService.updateSysUserLoginProfile(sysUserLoginProfile));
    }

    /**
     * 删除用户登录概况
     */
    @PreAuthorize("@ss.hasPermi('system:profile:remove')")
    @Log(title = "用户登录概况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{profileIds}")
    public AjaxResult remove(@PathVariable Long[] profileIds)
    {
        return toAjax(sysUserLoginProfileService.deleteSysUserLoginProfileByProfileIds(profileIds));
    }
}
