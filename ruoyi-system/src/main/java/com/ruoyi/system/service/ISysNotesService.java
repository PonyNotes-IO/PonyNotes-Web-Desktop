package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysNotes;

/**
 * 笔记 服务层
 * 
 * @author ruoyi
 */
public interface ISysNotesService
{
    /**
     * 查询笔记信息
     * 
     * @param id 笔记ID
     * @return 笔记信息
     */
    public SysNotes selectNotesById(Long id);

    /**
     * 查询笔记列表
     * 
     * @param notes 笔记信息
     * @return 笔记集合
     */
    public List<SysNotes> selectNotesList(SysNotes notes);

    /**
     * 新增笔记
     * 
     * @param notes 笔记信息
     * @return 结果
     */
    public int insertNotes(SysNotes notes);

    /**
     * 修改笔记
     * 
     * @param notes 笔记信息
     * @return 结果
     */
    public int updateNotes(SysNotes notes);

    /**
     * 删除笔记信息
     * 
     * @param id 笔记ID
     * @return 结果
     */
    public int deleteNotesById(Long id);
    
    /**
     * 批量删除笔记信息
     * 
     * @param ids 需要删除的笔记ID
     * @return 结果
     */
    public int deleteNotesByIds(Long[] ids);
}