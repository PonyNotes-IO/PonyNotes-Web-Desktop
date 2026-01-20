package com.ruoyi.xmbj.controller;

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
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.vo.AfWorkspaceVo;
import com.ruoyi.xmbj.service.IAfWorkspaceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/xmbj/workspace")
@DataSource(DataSourceType.SLAVE)
public class AfWorkspaceController extends BaseController
{
    @Autowired
    private IAfWorkspaceService afWorkspaceService;

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfWorkspace afWorkspace)
    {
        startPage();
        List<AfWorkspace> list = afWorkspaceService.selectAfWorkspaceList(afWorkspace);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:list')")
    @GetMapping("/listDetail")
    public TableDataInfo listDetail(AfWorkspace afWorkspace)
    {
        startPage();
        List<AfWorkspaceVo> list = afWorkspaceService.selectAfWorkspaceVoList(afWorkspace);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:export')")
    @Log(title = "工作空间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfWorkspace afWorkspace)
    {
        List<AfWorkspace> list = afWorkspaceService.selectAfWorkspaceList(afWorkspace);
        ExcelUtil<AfWorkspace> util = new ExcelUtil<AfWorkspace>(AfWorkspace.class);
        util.exportExcel(response, list, "工作空间数据");
    }

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:query')")
    @GetMapping(value = "/{workspaceId}")
    public AjaxResult getInfo(@PathVariable("workspaceId") String workspaceId)
    {
        return success(afWorkspaceService.selectAfWorkspaceByWorkspaceId(workspaceId));
    }

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:add')")
    @Log(title = "工作空间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfWorkspace afWorkspace)
    {
        return toAjax(afWorkspaceService.insertAfWorkspace(afWorkspace));
    }

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:edit')")
    @Log(title = "工作空间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfWorkspace afWorkspace)
    {
        return toAjax(afWorkspaceService.updateAfWorkspace(afWorkspace));
    }

    @PreAuthorize("@ss.hasPermi('xmbj:workspace:remove')")
    @Log(title = "工作空间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workspaceIds}")
    public AjaxResult remove(@PathVariable String[] workspaceIds)
    {
        return toAjax(afWorkspaceService.deleteAfWorkspaceByWorkspaceIds(workspaceIds));
    }
}
