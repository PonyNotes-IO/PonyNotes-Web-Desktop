package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.Addon;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AfSubscriptionAddons数据访问接口
 */
@Mapper
public interface AfSubscriptionAddonsMapper extends BaseMapper<AfSubscriptionAddons> {
    List<AfSubscriptionAddons> selectAddonsByType(String type);

    int updateAfSubscriptionAddons(AfSubscriptionAddons item);

    List<AfSubscriptionAddons> selectByIds(String addonId);
}