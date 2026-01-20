package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.Addon;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AfSubscriptionPlans数据访问接口
 */
@Mapper
public interface AfSubscriptionPlansMapper extends BaseMapper<AfSubscriptionPlans> {
    List<AfSubscriptionAddons> selectActiveAddons();

    List<Addon> selectAddonsByType(String type);

    List<AfSubscriptionPlans> selectActiveSubscriptionPlans();

    AfSubscriptionPlans selectByName(String planName);
}