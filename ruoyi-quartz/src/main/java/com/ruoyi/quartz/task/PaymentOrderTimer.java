package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.xmbj.domain.AfUserSubscriptions;
import com.ruoyi.xmbj.service.IAfUserSubscriptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@EnableScheduling
public class PaymentOrderTimer {
    private static final Logger log =  LoggerFactory.getLogger(PaymentOrderTimer.class);

    @Autowired
    private ISysPaymentService paymentService;

    @Autowired
    private IAfUserSubscriptionsService afUserSubscriptionsService;

    /**
     * 每小时检查一次过期订单（15分钟未支付）
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void handleExpiredOrders() {
        Date expireTime = DateUtils.addMinutes(new Date(), -15);
        List<SysPaymentOrder> expiredOrders = paymentService.selectExpiredOrders("pending", expireTime);
        if (!expiredOrders.isEmpty()) {
            log.info("处理过期订单数量：{}", expiredOrders.size());
            expiredOrders.forEach(order -> {
                order.setStatus("expired");
                paymentService.updateById(order);
            });
        }
    }

    @Scheduled(cron = "0 10/1 * * * ?")
    public void  handleProduct() {
        afUserSubscriptionsService.updateProductSubscriptionsPlan();
//        SELECT
//  id,uid,plan_id
//FROM
//  "public"."af_user_subscriptions" where status = 'active' and end_date < '2026-10-12'::date
//ORDER BY
//  end_date
//LIMIT
//  50

    }
}
