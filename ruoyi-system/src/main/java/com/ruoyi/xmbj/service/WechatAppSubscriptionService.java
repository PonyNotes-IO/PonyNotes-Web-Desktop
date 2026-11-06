package com.ruoyi.xmbj.service;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.mapper.WechatAppFulfillmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DataSource(DataSourceType.SLAVE)
public class WechatAppSubscriptionService {
    @Autowired
    private WechatAppFulfillmentMapper fulfillmentMapper;
    @Autowired
    private SubscriptionService subscriptionService;

    public void ensureReady() {
        // 下单前检查迁移，避免先扣款才发现会员库缺表。
        fulfillmentMapper.subscriptionId("");
    }

    @Transactional(rollbackFor = Exception.class)
    public Long fulfill(String orderNo, Long userId, Long planId, String billingType) {
        // PostgreSQL唯一键会等待并发事务；会员变更和处理记录在同一事务提交。
        if (fulfillmentMapper.claim(orderNo) == 0) {
            Long id = fulfillmentMapper.subscriptionId(orderNo);
            if (id == null) throw new IllegalStateException("微信App订单处理记录不完整");
            return id;
        }
        Long id = subscriptionService.subscribe(userId, planId, billingType).getId();
        if (id == null || fulfillmentMapper.complete(orderNo, id) != 1) {
            throw new IllegalStateException("微信App订单会员开通失败");
        }
        return id;
    }
}
