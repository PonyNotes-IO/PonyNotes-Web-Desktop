package com.ruoyi.xmbj.mapper;

import java.util.List;
import com.ruoyi.xmbj.domain.AfQuickNote;

/**
 * 快速笔记Mapper接口
 * 
 * @author 张继科
 * @date 2026-01-25
 */
public interface AfQuickNoteMapper 
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
     * 删除快速笔记
     * 
     * @param id 快速笔记主键
     * @return 结果
     */
    public int deleteAfQuickNoteById(String id);

    /**
     * 批量删除快速笔记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAfQuickNoteByIds(String[] ids);
}
