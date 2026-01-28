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
import com.ruoyi.system.domain.SysUserLoginRecord;
import com.ruoyi.system.service.ISysUserLoginRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户登录记录Controller
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/system/record")
public class SysUserLoginRecordController extends BaseController
{
    @Autowired
    private ISysUserLoginRecordService sysUserLoginRecordService;

    /**
     * 查询用户登录记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserLoginRecord sysUserLoginRecord)
    {
        System.out.println("SysUserLoginRecordController.list() - profileId: " + sysUserLoginRecord.getProfileId());
        startPage();
        List<SysUserLoginRecord> list = sysUserLoginRecordService.selectSysUserLoginRecordList(sysUserLoginRecord);
        System.out.println("SysUserLoginRecordController.list() - list size: " + list.size());
        return getDataTable(list);
    }

    /**
     * 导出用户登录记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:export')")
    @Log(title = "用户登录记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserLoginRecord sysUserLoginRecord)
    {
        List<SysUserLoginRecord> list = sysUserLoginRecordService.selectSysUserLoginRecordList(sysUserLoginRecord);
        ExcelUtil<SysUserLoginRecord> util = new ExcelUtil<SysUserLoginRecord>(SysUserLoginRecord.class);
        util.exportExcel(response, list, "用户登录记录数据");
    }

    /**
     * 获取用户登录记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(sysUserLoginRecordService.selectSysUserLoginRecordByRecordId(recordId));
    }

    /**
     * 新增用户登录记录
     */
    @PreAuthorize("@ss.hasPermi('system:record:add')")
    @Log(title = "用户登录记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserLoginRecord sysUserLoginRecord)
    {
        return toAjax(sysUserLoginRecordService.insertSysUserLoginRecord(sysUserLoginRecord));
    }

    /**
     * 修改用户登录记录
     */
    @PreAuthorize("@ss.hasPermi('system:record:edit')")
    @Log(title = "用户登录记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserLoginRecord sysUserLoginRecord)
    {
        return toAjax(sysUserLoginRecordService.updateSysUserLoginRecord(sysUserLoginRecord));
    }

    /**
     * 删除用户登录记录
     */
    @PreAuthorize("@ss.hasPermi('system:record:remove')")
    @Log(title = "用户登录记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(sysUserLoginRecordService.deleteSysUserLoginRecordByRecordIds(recordIds));
    }
}
