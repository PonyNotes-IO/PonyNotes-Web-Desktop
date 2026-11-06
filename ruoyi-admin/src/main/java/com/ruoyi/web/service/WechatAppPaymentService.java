package com.ruoyi.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.web.config.WechatPayConfig;
import com.ruoyi.xmbj.service.WechatAppSubscriptionService;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.payments.app.model.Amount;
import com.wechat.pay.java.service.payments.app.model.PrepayRequest;
import com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.app.model.QueryOrderByOutTradeNoRequest;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/** 独立的直连商户App支付，网页JSAPI和Native支付继续使用原有实现。 */
@Service
public class WechatAppPaymentService {
    @Autowired
    private WechatPayConfig config;
    @Autowired
    private ISysPaymentService orders;
    @Autowired
    private WechatAppSubscriptionService subscriptions;

    @Value("${payment.wechat.app-pay-app-id:wx3b1a7737f52a004b}")
    private String appId;
    @Value("${payment.wechat.app-notify-url:}")
    private String appNotifyUrl;

    private AppServiceExtension sdk() {
        return new AppServiceExtension.Builder().config(config.WechatPayConfig()).build();
    }

    public String createPayInfo(String orderNo, BigDecimal amount) {
        subscriptions.ensureReady();
        String notifyUrl = appNotifyUrl;
        if (notifyUrl.isEmpty()) {
            String legacyUrl = config.getNotifyUrl();
            if (!legacyUrl.endsWith("/callback/wechat")) {
                throw new IllegalArgumentException("请配置微信App专用的app-notify-url");
            }
            notifyUrl = legacyUrl + "/app";
        }
        if (!notifyUrl.startsWith("https://") || !notifyUrl.endsWith("/callback/wechat/app")) {
            throw new IllegalArgumentException("微信App通知地址必须为HTTPS并指向/callback/wechat/app");
        }
        PrepayRequest request = new PrepayRequest();
        request.setAppid(appId);
        request.setMchid(config.getMchId());
        request.setOutTradeNo(orderNo);
        request.setDescription("小马笔记会员充值");
        request.setNotifyUrl(notifyUrl);
        request.setTimeExpire(OffsetDateTime.now().plusMinutes(15).withNano(0)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        Amount money = new Amount();
        money.setTotal(amount.movePointRight(2).intValueExact());
        money.setCurrency("CNY");
        request.setAmount(money);
        PrepayWithRequestPaymentResponse response = sdk().prepayWithRequestPayment(request);
        JSONObject params = new JSONObject();
        params.put("appid", response.getAppid());
        params.put("partnerid", response.getPartnerId());
        params.put("prepayid", response.getPrepayId());
        params.put("package", response.getPackageVal());
        params.put("noncestr", response.getNonceStr());
        params.put("timestamp", response.getTimestamp());
        params.put("sign", response.getSign());
        return params.toJSONString();
    }

    public void handleNotification(RequestParam request) {
        Transaction transaction = new NotificationParser((NotificationConfig) config.WechatPayConfig())
                .parse(request, Transaction.class);
        if (transaction.getTradeState() != Transaction.TradeStateEnum.SUCCESS) {
            throw new IllegalArgumentException("非成功支付通知");
        }
        complete(transaction, orders.selectOne(transaction.getOutTradeNo()));
    }

    public String checkStatus(SysPaymentOrder order) {
        if ("success".equals(order.getStatus())) return "success";
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(config.getMchId());
        request.setOutTradeNo(order.getOrderNo());
        Transaction transaction = sdk().queryOrderByOutTradeNo(request);
        return checkStatus(order, transaction);
    }

    String checkStatus(SysPaymentOrder order, Transaction transaction) {
        validateTransaction(transaction, order);
        if (transaction.getTradeState() == Transaction.TradeStateEnum.SUCCESS) {
            complete(transaction, order);
            return "success";
        }
        if (transaction.getTradeState() == Transaction.TradeStateEnum.CLOSED
                || transaction.getTradeState() == Transaction.TradeStateEnum.REVOKED
                || transaction.getTradeState() == Transaction.TradeStateEnum.PAYERROR) {
            return "failed";
        }
        return "pending";
    }

    private void complete(Transaction transaction, SysPaymentOrder order) {
        validateTransaction(transaction, order);
        if (transaction.getTradeState() != Transaction.TradeStateEnum.SUCCESS
                || transaction.getTransactionId() == null || transaction.getSuccessTime() == null) {
            throw new IllegalArgumentException("微信App交易未完成");
        }
        if ("success".equals(order.getStatus())) return;
        Date paidAt = Date.from(OffsetDateTime.parse(transaction.getSuccessTime()).toInstant());
        Long subscriptionId = subscriptions.fulfill(order.getOrderNo(), Long.valueOf(order.getClientUserId()),
                Long.valueOf(order.getPlanId()), order.getBillingType());
        order.setClientSubscriptionId(subscriptionId.toString());
        order.setStatus("success");
        order.setPayTime(paidAt);
        order.setUpdateTime(new Date());
        if (orders.updateById(order) != 1) throw new IllegalStateException("微信App订单更新失败");
    }

    void validateTransaction(Transaction transaction, SysPaymentOrder order) {
        if (transaction == null || transaction.getTradeState() == null
                || order == null || !"wechat_app".equals(order.getPaymentType())
                || !order.getOrderNo().equals(transaction.getOutTradeNo())
                || !appId.equals(transaction.getAppid()) || !config.getMchId().equals(transaction.getMchid())
                || (transaction.getTradeType() != null && transaction.getTradeType() != Transaction.TradeTypeEnum.APP)) {
            throw new IllegalArgumentException("微信App交易与订单不匹配");
        }
        // 未付款/已关闭的查单响应可能没有支付字段；开通会员前仍须完整校验。
        if (transaction.getTradeState() == Transaction.TradeStateEnum.SUCCESS
                && (transaction.getTradeType() != Transaction.TradeTypeEnum.APP
                || transaction.getAmount() == null || transaction.getAmount().getTotal() == null
                || !"CNY".equals(transaction.getAmount().getCurrency())
                || order.getAmount().movePointRight(2).intValueExact() != transaction.getAmount().getTotal())) {
            throw new IllegalArgumentException("微信App交易与订单不匹配");
        }
    }
}
