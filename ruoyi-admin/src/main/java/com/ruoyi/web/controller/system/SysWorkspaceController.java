package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.system.domain.SysWorkspace;
import com.ruoyi.system.service.ISysWorkspaceService;

/**
 * 工作空间 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/workspace")
public class SysWorkspaceController extends BaseController
{
    @Autowired
    private ISysWorkspaceService sysWorkspaceService;

    /**
     * 获取工作空间列表
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWorkspace sysWorkspace)
    {
        startPage();
        List<SysWorkspace> list = sysWorkspaceService.selectWorkspaceList(sysWorkspace);
        return getDataTable(list);
    }

    /**
     * 根据工作空间ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:query')")
    @GetMapping("/{workspaceId}")
    public AjaxResult getInfo(@PathVariable("workspaceId") String workspaceId)
    {
        return AjaxResult.success(sysWorkspaceService.selectWorkspaceById(workspaceId));
    }

    /**
     * 新增工作空间
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:add')")
    @Log(title = "工作空间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysWorkspace sysWorkspace)
    {
        return toAjax(sysWorkspaceService.insertWorkspace(sysWorkspace));
    }

    /**
     * 修改工作空间
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:edit')")
    @Log(title = "工作空间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysWorkspace sysWorkspace)
    {
        return toAjax(sysWorkspaceService.updateWorkspace(sysWorkspace));
    }

    /**
     * 删除工作空间
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:remove')")
    @Log(title = "工作空间", businessType = BusinessType.DELETE)
    @DeleteMapping("/{workspaceId}")
    public AjaxResult remove(@PathVariable String workspaceId)
    {
        return toAjax(sysWorkspaceService.deleteWorkspaceById(workspaceId));
    }

    /**
     * 批量删除工作空间
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:remove')")
    @Log(title = "工作空间", businessType = BusinessType.DELETE)
    @DeleteMapping("/batch")
    public AjaxResult removeBatch(@RequestBody String[] workspaceIds)
    {
        return toAjax(sysWorkspaceService.deleteWorkspaceByIds(workspaceIds));
    }
}