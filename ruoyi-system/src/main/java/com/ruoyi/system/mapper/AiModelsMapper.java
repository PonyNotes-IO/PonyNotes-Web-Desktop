package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AiModels;

/**
 * AI模型管理Mapper接口
 * 
 * @author ruoyi
 * @date 2026-01-25
 */
public interface AiModelsMapper 
{
    /**
     * 查询AI模型管理
     * 
     * @param id AI模型管理主键
     * @return AI模型管理
     */
    public AiModels selectAiModelsById(Long id);

    /**
     * 查询AI模型管理列表
     * 
     * @param aiModels AI模型管理
     * @return AI模型管理集合
     */
    public List<AiModels> selectAiModelsList(AiModels aiModels);

    /**
     * 新增AI模型管理
     * 
     * @param aiModels AI模型管理
     * @return 结果
     */
    public int insertAiModels(AiModels aiModels);

    /**
     * 修改AI模型管理
     * 
     * @param aiModels AI模型管理
     * @return 结果
     */
    public int updateAiModels(AiModels aiModels);

    /**
     * 删除AI模型管理
     * 
     * @param id AI模型管理主键
     * @return 结果
     */
    public int deleteAiModelsById(Long id);

    /**
     * 批量删除AI模型管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAiModelsByIds(Long[] ids);
}
