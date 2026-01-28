package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysNotes;
import com.ruoyi.system.mapper.SysNotesMapper;
import com.ruoyi.system.service.ISysNotesService;

/**
 * 笔记 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysNotesServiceImpl implements ISysNotesService
{
    @Autowired
    private SysNotesMapper notesMapper;

    /**
     * 查询笔记信息
     * 
     * @param id 笔记ID
     * @return 笔记信息
     */
    @Override
    public SysNotes selectNotesById(Long id)
    {
        return notesMapper.selectNotesById(id);
    }

    /**
     * 查询笔记列表
     * 
     * @param notes 笔记信息
     * @return 笔记集合
     */
    @Override
    public List<SysNotes> selectNotesList(SysNotes notes)
    {
        return notesMapper.selectNotesList(notes);
    }

    /**
     * 新增笔记
     * 
     * @param notes 笔记信息
     * @return 结果
     */
    @Override
    public int insertNotes(SysNotes notes)
    {
        return notesMapper.insertNotes(notes);
    }

    /**
     * 修改笔记
     * 
     * @param notes 笔记信息
     * @return 结果
     */
    @Override
    public int updateNotes(SysNotes notes)
    {
        return notesMapper.updateNotes(notes);
    }

    /**
     * 删除笔记对象
     * 
     * @param id 笔记ID
     * @return 结果
     */
    @Override
    public int deleteNotesById(Long id)
    {
        return notesMapper.deleteNotesById(id);
    }

    /**
     * 批量删除笔记信息
     * 
     * @param ids 需要删除的笔记ID
     * @return 结果
     */
    @Override
    public int deleteNotesByIds(Long[] ids)
    {
        return notesMapper.deleteNotesByIds(ids);
    }
}