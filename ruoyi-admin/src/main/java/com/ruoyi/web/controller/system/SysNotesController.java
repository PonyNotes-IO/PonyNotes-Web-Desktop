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
import com.ruoyi.system.domain.SysNotes;
import com.ruoyi.system.service.ISysNotesService;

/**
 * 笔记 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notes")
public class SysNotesController extends BaseController
{
    @Autowired
    private ISysNotesService notesService;

    /**
     * 获取笔记列表
     */
    @PreAuthorize("@ss.hasPermi('system:notes:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotes notes)
    {
        startPage();
        List<SysNotes> list = notesService.selectNotesList(notes);
        return getDataTable(list);
    }

    /**
     * 根据笔记编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(notesService.selectNotesById(id));
    }

    /**
     * 新增笔记
     */
    @PreAuthorize("@ss.hasPermi('system:notes:add')")
    @Log(title = "笔记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotes notes)
    {
        notes.setCreateBy(getUsername());
        return toAjax(notesService.insertNotes(notes));
    }

    /**
     * 修改笔记
     */
    @PreAuthorize("@ss.hasPermi('system:notes:edit')")
    @Log(title = "笔记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotes notes)
    {
        notes.setUpdateBy(getUsername());
        return toAjax(notesService.updateNotes(notes));
    }

    /**
     * 删除笔记
     */
    @PreAuthorize("@ss.hasPermi('system:notes:remove')")
    @Log(title = "笔记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(notesService.deleteNotesByIds(ids));
    }
}