package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.AiModelConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AI模型配置数据访问接口
 */
@Mapper
public interface AiModelConfigMapper extends BaseMapper<AiModelConfig> {

    /**
     * 查询所有活跃的AI模型配置
     */
    List<AiModelConfig> selectActiveAiModelConfigs();

    /**
     * 根据ID查询AI模型配置
     */
    AiModelConfig selectAiModelConfigById(@Param("id") Long id);

    /**
     * 查询AI模型配置列表
     */
    List<AiModelConfig> selectAiModelConfigList(AiModelConfig aiModelConfig);

    /**
     * 新增AI模型配置
     */
    int insertAiModelConfig(AiModelConfig aiModelConfig);

    /**
     * 更新AI模型配置
     */
    int updateAiModelConfig(AiModelConfig aiModelConfig);

    /**
     * 批量删除AI模型配置
     */
    int deleteAiModelConfigByIds(@Param("ids") Long[] ids);

    /**
     * 启用AI模型配置
     */
    int enableAiModelConfig(@Param("id") Long id);

    /**
     * 停用AI模型配置
     */
    int disableAiModelConfig(@Param("id") Long id);
}