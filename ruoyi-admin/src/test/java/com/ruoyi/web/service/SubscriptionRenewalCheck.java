package com.ruoyi.web.service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.web.service.impl.PaymentServiceImpl;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.domain.AfUserSubscriptions;
import com.ruoyi.xmbj.domain.UserUsage;
import com.ruoyi.xmbj.mapper.AfSubscriptionPlansMapper;
import com.ruoyi.xmbj.mapper.AfUserSubscriptionsMapper;
import com.ruoyi.xmbj.mapper.UserUsageMapper;
import com.ruoyi.xmbj.service.SubscriptionService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Date;

/** Run main with the ruoyi-admin test classpath; no payment or database requests. */
public class SubscriptionRenewalCheck {
    private static AfUserSubscriptions current;
    private static int inserts;

    public static void main(String[] args) throws Exception {
        checkRenewal("monthly", "monthly", "2096-01-31 12:00:00", "2096-02-29 12:00:00");
        checkRenewal("monthly", "yearly", "2096-02-29 12:00:00", "2097-02-28 12:00:00");
        checkRenewal("yearly", "monthly", "2096-01-31 12:00:00", "2096-02-29 12:00:00");
        checkRenewal("yearly", "yearly", "2096-02-29 12:00:00", "2097-02-28 12:00:00");
        checkRenewal("monthly", "monthly", "2000-01-01 12:00:00", null);
        System.out.println("Subscription renewal checks passed (paid callback, monthly/yearly, expired, replay).");
    }

    private static void checkRenewal(String oldPeriod, String period, String oldEnd, String expectedEnd)
            throws Exception {
        Date start = DateUtils.parseDate("2000-01-01 00:00:00");
        current = AfUserSubscriptions.builder().id(10L).uid(9L).planId(3L)
                .billingType(oldPeriod).status("active").startDate(start)
                .endDate(DateUtils.parseDate(oldEnd)).build();
        inserts = 0;
        AfSubscriptionPlans plan = new AfSubscriptionPlans();
        plan.setId(3L);
        SubscriptionService subscriptions = new SubscriptionService();
        set(subscriptions, "afSubscriptionPlansMapper", proxy(AfSubscriptionPlansMapper.class,
                (p, method, values) -> {
                    if (method.getName().equals("selectAfSubscriptionPlansById")) return plan;
                    throw new AssertionError(method.getName());
                }));
        set(subscriptions, "afUserSubscriptionsMapper", proxy(AfUserSubscriptionsMapper.class,
                (p, method, values) -> {
                    switch (method.getName()) {
                        case "selectCurrentActiveSubscription": return Collections.singletonList(current);
                        case "updateUserSubscriptions": return 1;
                        case "insert":
                            current = (AfUserSubscriptions) values[0];
                            current.setId(11L);
                            inserts++;
                            return null;
                        default: throw new AssertionError(method.getName());
                    }
                }));
        set(subscriptions, "userUsageMapper", proxy(UserUsageMapper.class,
                (p, method, values) -> {
                    if (method.getName().equals("selectUserUsage")) return new UserUsage();
                    throw new AssertionError(method.getName());
                }));

        SysPaymentOrder order = new SysPaymentOrder();
        order.setOrderNo("renewal-check");
        order.setPaymentType("alipay");
        order.setStatus("pending");
        order.setPlanId("3");
        order.setClientUserId("9");
        order.setBillingType(period);
        PaymentServiceImpl payment = new PaymentServiceImpl();
        set(payment, "subscriptionService", subscriptions);
        set(payment, "paymentService", proxy(ISysPaymentService.class,
                (p, method, values) -> {
                    if (method.getName().equals("selectOne")) return order;
                    if (method.getName().equals("updateById")) return 1;
                    throw new AssertionError(method.getName());
                }));

        Date before = new Date();
        check(payment.handlePaymentCallback(order.getOrderNo(), "alipay"), "Paid order was not fulfilled");
        Date after = new Date();
        check(current.getPlanId().equals(3L), "Renewal changed membership tier");
        check("active".equals(current.getStatus()), "Renewed membership is not active");
        check("success".equals(order.getStatus()), "Paid order was not completed");
        check("11".equals(order.getClientSubscriptionId()), "Order does not reference renewed subscription");
        if (expectedEnd != null) {
            check(current.getEndDate().equals(DateUtils.parseDate(expectedEnd)),
                    "Renewal did not extend the original expiry: " + current.getEndDate());
            check(current.getStartDate().equals(start), "Active renewal changed the original start date");
        } else {
            check(!current.getEndDate().before(DateUtils.addMonths(before, 1))
                            && !current.getEndDate().after(DateUtils.addMonths(after, 1)),
                    "Expired membership was not renewed from payment time");
        }
        Date renewedEnd = current.getEndDate();
        check(!payment.handlePaymentCallback(order.getOrderNo(), "alipay"), "Repeated callback was accepted");
        check(inserts == 1 && current.getEndDate().equals(renewedEnd), "Repeated callback extended expiry twice");
    }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }

    private static void set(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
