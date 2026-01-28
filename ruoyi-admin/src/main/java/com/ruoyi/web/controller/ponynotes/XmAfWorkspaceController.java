package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xmbj.domain.AfUserWorkspace;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.NoteBook;
import com.ruoyi.xmbj.service.AfWorkspaceService;
import org.apache.poi.sl.usermodel.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponynotes/afworkspaces")
public class XmAfWorkspaceController extends BaseController {
    @Autowired
    private AfWorkspaceService afWorkspaceService;



    /**
     * 查询工作区列表
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:userworkspacelist')")
    @GetMapping("/userworkspacelist")
    public TableDataInfo userworkspacelist(AfUserWorkspace afUserWorkspace) {
        startPage();
        List<AfUserWorkspace> list = afWorkspaceService.selectAfUserWorkspaceList(afUserWorkspace);
        return getDataTable(list);
    }

    /**
     * 查询工作区列表
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfWorkspace afWorkspace) {
        startPage();
        List<AfWorkspace> list = afWorkspaceService.selectAfWorkspaceList(afWorkspace);
        return getDataTable(list);
    }

    /**
     * 导出工作区列表
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:export')")
    @Log(title = "工作区", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AfWorkspace afWorkspace) {
        List<AfWorkspace> list = afWorkspaceService.selectAfWorkspaceList(afWorkspace);
        ExcelUtil<AfWorkspace> util = new ExcelUtil<AfWorkspace>(AfWorkspace.class);
        return util.exportExcel(list, "工作区数据");
    }

    /**
     * 获取工作区详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:query')")
    @GetMapping(value = "/{workspaceId}")
    public AjaxResult getInfo(@PathVariable("workspaceId") String workspaceId) {
        return success(afWorkspaceService.selectAfWorkspaceByWorkspaceId(workspaceId));
    }

    /**
     * 新增工作区
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:add')")
    @Log(title = "工作区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfWorkspace afWorkspace) {
        return toAjax(afWorkspaceService.insertAfWorkspace(afWorkspace));
    }

    /**
     * 修改工作区
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:edit')")
    @Log(title = "工作区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfWorkspace afWorkspace) {
        return toAjax(afWorkspaceService.updateAfWorkspace(afWorkspace));
    }

    /**
     * 删除工作区
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:remove')")
    @Log(title = "工作区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{workspaceIds}")
    public AjaxResult remove(@PathVariable String[] workspaceIds) {
        return toAjax(afWorkspaceService.deleteAfWorkspaceByWorkspaceIds(workspaceIds));
    }

    /**
     * 通过空间ID获取笔记
     */
    /**
     * 获取工作区详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:workspace:notes')")
    @GetMapping(value = "/getnotes/{workspaceId}/{uid}")
    public TableDataInfo getNotesByWorkspaceId(@PathVariable("workspaceId") String workspaceId,@PathVariable("uid") String uid) {
        startPage();
        List<NoteBook> list = afWorkspaceService.getNotesByWorkspaceId(workspaceId,uid);
        return getDataTable(list);
    }

    /**
     * 获取工作区详细信息
     */
    @GetMapping(value = "/getnotes/{oid}")
    public AjaxResult getNotesByNoteId(@PathVariable("oid") String oid) {
        if(StringUtils.isEmpty(oid)){
            return  AjaxResult.error("notebook id 为空");
        }
        NoteBook noteBook = afWorkspaceService.getNotesByNoteId(oid);
        return AjaxResult.success(noteBook);
    }

}
