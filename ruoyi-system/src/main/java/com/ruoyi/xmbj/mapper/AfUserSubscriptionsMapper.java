package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.AfUserSubscriptions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AfUserSubscriptions数据访问接口
 */
@Mapper
public interface AfUserSubscriptionsMapper extends BaseMapper<AfUserSubscriptions> {
    AfUserSubscriptions selectCurrentActiveSubscription(Long userId);

    /**
     * 查询用户的所有订阅记录
     */
    List<AfUserSubscriptions> selectUserSubscriptions(Long userId);

    /**
     * 检查用户订阅是否有效（未过期）
     */
    int checkActiveSubscription(Long userId);

    /**
     * 更新订阅状态为过期
     */
    int updateExpiredSubscriptions(Long userId);

    AfUserSubscriptions selectUserSubscriptionById(Long clientSubscriptionId);
}