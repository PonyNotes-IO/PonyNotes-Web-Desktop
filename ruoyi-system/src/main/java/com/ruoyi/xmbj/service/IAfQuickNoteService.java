package com.ruoyi.xmbj.service;

import java.util.List;
import com.ruoyi.xmbj.domain.AfQuickNote;

/**
 * 快速笔记Service接口
 * 
 * @author 张继科
 * @date 2026-01-25
 */
public interface IAfQuickNoteService 
{
    /**
     * 查询快速笔记
     * 
     * @param id 快速笔记主键
     * @return 快速笔记
     */
    public AfQuickNote selectAfQuickNoteById(String id);

    /**
     * 查询快速笔记列表
     * 
     * @param afQuickNote 快速笔记
     * @return 快速笔记集合
     */
    public List<AfQuickNote> selectAfQuickNoteList(AfQuickNote afQuickNote);

    /**
     * 新增快速笔记
     * 
     * @param afQuickNote 快速笔记
     * @return 结果
     */
    public int insertAfQuickNote(AfQuickNote afQuickNote);

    /**
     * 修改快速笔记
     * 
     * @param afQuickNote 快速笔记
     * @return 结果
     */
    public int updateAfQuickNote(AfQuickNote afQuickNote);

    /**
     * 批量删除快速笔记
     * 
     * @param ids 需要删除的快速笔记主键集合
     * @return 结果
     */
    public int deleteAfQuickNoteByIds(String[] ids);

    /**
     * 删除快速笔记信息
     * 
     * @param id 快速笔记主键
     * @return 结果
     */
    public int deleteAfQuickNoteById(String id);
}
