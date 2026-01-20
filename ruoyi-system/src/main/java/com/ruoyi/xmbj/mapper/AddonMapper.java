package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.Addon;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 补充包数据访问接口
 */
@Mapper
public interface AddonMapper extends BaseMapper<Addon> {

    /**
     * 查询所有活跃的补充包
     */
    List<AfSubscriptionAddons> selectActiveAddons();

    /**
     * 按类型查询补充包
     */
    List<AfSubscriptionAddons> selectAddonsByType(String type);
}
