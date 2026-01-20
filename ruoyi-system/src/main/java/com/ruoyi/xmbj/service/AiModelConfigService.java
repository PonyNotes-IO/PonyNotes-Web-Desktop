package com.ruoyi.xmbj.service;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.xmbj.domain.AiModelConfig;
import com.ruoyi.xmbj.mapper.AiModelConfigMapper;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI模型配置业务逻辑
 * 所有查询操作使用 PostgreSQL 从库数据源（@DataSource(DataSourceType.slave)）
 * 所有写入操作使用主库数据源（MySQL）
 */
@Slf4j
@Service
@Transactional
public class AiModelConfigService {

    @Autowired
    private AiModelConfigMapper aiModelConfigMapper;

    /**
     * 查询AI模型配置列表
     */
    @DataSource(DataSourceType.slave)
    public List<AiModelConfig> selectAiModelConfigList(AiModelConfig aiModelConfig) {
        return aiModelConfigMapper.selectList(null); // 简化，实际可添加条件查询
    }

    /**
     * 根据ID查询AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public AiModelConfig selectAiModelConfigById(Long id) {
        return aiModelConfigMapper.selectById(id);
    }

    /**
     * 新增AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public int insertAiModelConfig(AiModelConfig aiModelConfig) {
        aiModelConfig.setCreateTime(LocalDateTime.now());
        aiModelConfig.setUpdateTime(LocalDateTime.now());
        aiModelConfig.setOperator(SecurityUtils.getUsername());
        aiModelConfig.setOperatorId(String.valueOf(SecurityUtils.getUserId()));
        return aiModelConfigMapper.insert(aiModelConfig);
    }

    /**
     * 更新AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public int updateAiModelConfig(AiModelConfig aiModelConfig) {
        aiModelConfig.setUpdateTime(LocalDateTime.now());
        aiModelConfig.setOperator(SecurityUtils.getUsername());
        aiModelConfig.setOperatorId(String.valueOf(SecurityUtils.getUserId()));
        return aiModelConfigMapper.updateById(aiModelConfig);
    }

    /**
     * 删除AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public int deleteAiModelConfigByIds(Long[] ids) {
        return aiModelConfigMapper.deleteBatchIds(java.util.Arrays.asList(ids));
    }

    /**
     * 停用AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public int disableAiModelConfig(Long id) {
        AiModelConfig aiModelConfig = new AiModelConfig();
        aiModelConfig.setId(id);
        aiModelConfig.setStatus("停用");
        aiModelConfig.setUpdateTime(LocalDateTime.now());
        aiModelConfig.setOperator(SecurityUtils.getUsername());
        aiModelConfig.setOperatorId(String.valueOf(SecurityUtils.getUserId()));
        return aiModelConfigMapper.updateById(aiModelConfig);
    }

    /**
     * 启用AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public int enableAiModelConfig(Long id) {
        AiModelConfig aiModelConfig = new AiModelConfig();
        aiModelConfig.setId(id);
        aiModelConfig.setStatus("正常");
        aiModelConfig.setUpdateTime(LocalDateTime.now());
        aiModelConfig.setOperator(SecurityUtils.getUsername());
        aiModelConfig.setOperatorId(String.valueOf(SecurityUtils.getUserId()));
        return aiModelConfigMapper.updateById(aiModelConfig);
    }

    /**
     * 查询所有活跃的AI模型配置
     */
    @DataSource(DataSourceType.slave)
    public List<AiModelConfig> selectActiveAiModelConfigs() {
        return aiModelConfigMapper.selectActiveAiModelConfigs();
    }
}