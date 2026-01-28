package com.ruoyi.xmbj.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xmbj.mapper.AfQuickNoteMapper;
import com.ruoyi.xmbj.domain.AfQuickNote;
import com.ruoyi.xmbj.service.IAfQuickNoteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.mapper.AfSubscriptionAddonsMapper;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.service.IAfSubscriptionAddonsService;
/**
 * 快速笔记Service业务层处理
 * 
 * @author 张继科
 * @date 2026-01-25
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class AfQuickNoteServiceImpl implements IAfQuickNoteService 
{
    @Autowired
    private AfQuickNoteMapper afQuickNoteMapper;

    /**
     * 查询快速笔记
     * 
     * @param id 快速笔记主键
     * @return 快速笔记
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public AfQuickNote selectAfQuickNoteById(String id)
    {
        return afQuickNoteMapper.selectAfQuickNoteById(id);
    }

    /**
     * 查询快速笔记列表
     * 
     * @param afQuickNote 快速笔记
     * @return 快速笔记
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<AfQuickNote> selectAfQuickNoteList(AfQuickNote afQuickNote)
    {
        return afQuickNoteMapper.selectAfQuickNoteList(afQuickNote);
    }

    /**
     * 新增快速笔记
     * 
     * @param afQuickNote 快速笔记
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int insertAfQuickNote(AfQuickNote afQuickNote)
    {
        return afQuickNoteMapper.insertAfQuickNote(afQuickNote);
    }

    /**
     * 修改快速笔记
     * 
     * @param afQuickNote 快速笔记
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int updateAfQuickNote(AfQuickNote afQuickNote)
    {
        return afQuickNoteMapper.updateAfQuickNote(afQuickNote);
    }

    /**
     * 批量删除快速笔记
     * 
     * @param ids 需要删除的快速笔记主键
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfQuickNoteByIds(String[] ids)
    {
        return afQuickNoteMapper.deleteAfQuickNoteByIds(ids);
    }

    /**
     * 删除快速笔记信息
     * 
     * @param id 快速笔记主键
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfQuickNoteById(String id)
    {
        return afQuickNoteMapper.deleteAfQuickNoteById(id);
    }
}
