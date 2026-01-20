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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xmbj.domain.AfQuickNote;
import com.ruoyi.xmbj.service.IAfQuickNoteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 快速笔记Controller
 * 
 * @author 张继科
 * @date 2026-01-25
 */
@RestController
@RequestMapping("/xmbj/note")
public class AfQuickNoteController extends BaseController
{
    @Autowired
    private IAfQuickNoteService afQuickNoteService;

    /**
     * 查询快速笔记列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:note:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfQuickNote afQuickNote)
    {
        startPage();
        List<AfQuickNote> list = afQuickNoteService.selectAfQuickNoteList(afQuickNote);
        return getDataTable(list);
    }

    /**
     * 导出快速笔记列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:note:export')")
    @Log(title = "快速笔记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfQuickNote afQuickNote)
    {
        List<AfQuickNote> list = afQuickNoteService.selectAfQuickNoteList(afQuickNote);
        ExcelUtil<AfQuickNote> util = new ExcelUtil<AfQuickNote>(AfQuickNote.class);
        util.exportExcel(response, list, "快速笔记数据");
    }

    /**
     * 获取快速笔记详细信息
     */
    @PreAuthorize("@ss.hasPermi('xmbj:note:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(afQuickNoteService.selectAfQuickNoteById(id));
    }

    /**
     * 新增快速笔记
     */
    @PreAuthorize("@ss.hasPermi('xmbj:note:add')")
    @Log(title = "快速笔记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfQuickNote afQuickNote)
    {
        return toAjax(afQuickNoteService.insertAfQuickNote(afQuickNote));
    }

    /**
     * 修改快速笔记
     */
    @PreAuthorize("@ss.hasPermi('xmbj:note:edit')")
    @Log(title = "快速笔记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfQuickNote afQuickNote)
    {
        return toAjax(afQuickNoteService.updateAfQuickNote(afQuickNote));
    }

    /**
     * 删除快速笔记
     */
    @PreAuthorize("@ss.hasPermi('xmbj:note:remove')")
    @Log(title = "快速笔记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(afQuickNoteService.deleteAfQuickNoteByIds(ids));
    }
}
