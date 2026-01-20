package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AiModelsMapper;
import com.ruoyi.system.domain.AiModels;
import com.ruoyi.system.service.IAiModelsService;

/**
 * AI模型管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-25
 */
@Service
public class AiModelsServiceImpl implements IAiModelsService 
{
    @Autowired
    private AiModelsMapper aiModelsMapper;

    /**
     * 查询AI模型管理
     * 
     * @param id AI模型管理主键
     * @return AI模型管理
     */
    @Override
    public AiModels selectAiModelsById(Long id)
    {
        return aiModelsMapper.selectAiModelsById(id);
    }

    /**
     * 查询AI模型管理列表
     * 
     * @param aiModels AI模型管理
     * @return AI模型管理
     */
    @Override
    public List<AiModels> selectAiModelsList(AiModels aiModels)
    {
        return aiModelsMapper.selectAiModelsList(aiModels);
    }

    /**
     * 新增AI模型管理
     * 
     * @param aiModels AI模型管理
     * @return 结果
     */
    @Override
    public int insertAiModels(AiModels aiModels)
    {
        return aiModelsMapper.insertAiModels(aiModels);
    }

    /**
     * 修改AI模型管理
     * 
     * @param aiModels AI模型管理
     * @return 结果
     */
    @Override
    public int updateAiModels(AiModels aiModels)
    {
        return aiModelsMapper.updateAiModels(aiModels);
    }

    /**
     * 批量删除AI模型管理
     * 
     * @param ids 需要删除的AI模型管理主键
     * @return 结果
     */
    @Override
    public int deleteAiModelsByIds(Long[] ids)
    {
        return aiModelsMapper.deleteAiModelsByIds(ids);
    }

    /**
     * 删除AI模型管理信息
     * 
     * @param id AI模型管理主键
     * @return 结果
     */
    @Override
    public int deleteAiModelsById(Long id)
    {
        return aiModelsMapper.deleteAiModelsById(id);
    }
}
