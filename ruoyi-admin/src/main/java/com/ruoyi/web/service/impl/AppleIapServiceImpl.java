package com.ruoyi.web.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.web.config.AppleIapConfig;
import com.ruoyi.web.service.AppleIapService;
import com.ruoyi.web.service.PaymentService;
import com.ruoyi.web.util.AppleJwsVerifier;
import com.ruoyi.web.util.OrderNoGenerator;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.domain.ClientUser;
import com.ruoyi.xmbj.service.ClientUserService;
import com.ruoyi.xmbj.service.IAfSubscriptionPlansService;
import com.ruoyi.xmbj.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Apple 应用内购买（StoreKit 2）服务实现。
 *
 * 流程与微信/支付宝的差异：下单和扣款都发生在苹果设备上（StoreKit 2），
 * 客户端支付成功后把签名交易（jwsRepresentation）提交到本服务验证，
 * 验签通过后落库并复用 handlePaymentCallback 的发货逻辑开通订阅。
 */
@Service
public class AppleIapServiceImpl implements AppleIapService {

    private static final Logger log = LoggerFactory.getLogger(AppleIapServiceImpl.class);

    @Value("${ruoyi.payment-debug:Y}")
    private String paymentDebug;

    @Autowired
    private AppleIapConfig appleIapConfig;

    @Autowired
    private ISysPaymentService paymentService;

    @Autowired
    private PaymentService paymentServiceImpl;

    @Autowired
    private IAfSubscriptionPlansService iAfSubscriptionPlansService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public SysPaymentOrder verifyTransaction(String signedTransaction, String userInfo) {
        checkEnabled();
        if (StringUtils.isEmpty(userInfo)) {
            throw new IllegalArgumentException("用户未登录,请登录");
        }
        ClientUser clientUser = clientUserService.getClientUserByUuid(userInfo);
        if (clientUser == null) {
            throw new IllegalArgumentException("小马笔记客户端用户不存在");
        }
        return processTransaction(signedTransaction, clientUser);
    }

    @Override
    public List<SysPaymentOrder> restoreTransactions(List<String> signedTransactions, String userInfo) {
        checkEnabled();
        if (StringUtils.isEmpty(userInfo)) {
            throw new IllegalArgumentException("用户未登录,请登录");
        }
        ClientUser clientUser = clientUserService.getClientUserByUuid(userInfo);
        if (clientUser == null) {
            throw new IllegalArgumentException("小马笔记客户端用户不存在");
        }
        List<SysPaymentOrder> orders = new ArrayList<>();
        if (signedTransactions == null) {
            return orders;
        }
        for (String jws : signedTransactions) {
            try {
                orders.add(processTransaction(jws, clientUser));
            } catch (Exception e) {
                // 恢复购买时单条失败不影响其余交易
                log.warn("恢复购买单笔交易处理失败：{}", e.getMessage());
            }
        }
        return orders;
    }

    /**
     * 验签 + 落库 + 开通（幂等：同一 apple transactionId 只处理一次）
     */
    private SysPaymentOrder processTransaction(String signedTransaction, ClientUser clientUser) {
        // 1. 验签并解析交易
        JsonNode tx = AppleJwsVerifier.getInstance().verifyAndDecode(signedTransaction);

        String transactionId = tx.path("transactionId").asText(null);
        String originalTransactionId = tx.path("originalTransactionId").asText(null);
        String productId = tx.path("productId").asText(null);
        String bundleId = tx.path("bundleId").asText(null);
        String environment = tx.path("environment").asText(null);
        String revocationDate = tx.path("revocationDate").asText(null);

        if (StringUtils.isEmpty(transactionId) || StringUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("交易数据缺少 transactionId/productId");
        }

        // 2. 校验 bundleId 与环境
        if (StringUtils.isNotEmpty(appleIapConfig.getBundleId())
                && !appleIapConfig.getBundleId().equals(bundleId)) {
            throw new IllegalArgumentException("交易 bundleId 不匹配：" + bundleId);
        }
        if (StringUtils.isNotEmpty(environment)
                && StringUtils.isNotEmpty(appleIapConfig.getEnvironment())
                && !appleIapConfig.getEnvironment().equalsIgnoreCase(environment)) {
            throw new IllegalArgumentException("交易环境不匹配：" + environment
                    + "，当前配置：" + appleIapConfig.getEnvironment());
        }

        // 3. 已撤销的交易（家庭共享被撤回等）不开通
        if (StringUtils.isNotEmpty(revocationDate) && !"0".equals(revocationDate)) {
            throw new IllegalArgumentException("交易已被苹果撤销");
        }

        // 4. 幂等：同一 transactionId 已成功发货直接返回；残留 pending（首次发货失败）则补发货
        SysPaymentOrder exist = paymentService.selectByAppleTransactionId(transactionId);
        if (exist != null) {
            if (!"success".equals(exist.getStatus())) {
                paymentServiceImpl.handlePaymentCallback(exist.getOrderNo(), "apple");
                exist = paymentService.selectOne(exist.getOrderNo());
            }
            return exist;
        }

        // 5. 商品ID映射本地套餐：规则为 {productPrefix}{monthly|yearly}.{套餐编码planCode}
        //    如 com.ponynotes.monthly.standard -> 套餐编码 standard 的月付
        String prefix = appleIapConfig.getProductPrefix();
        if (StringUtils.isEmpty(prefix) || !productId.startsWith(prefix)) {
            throw new IllegalArgumentException("苹果商品ID必须以 " + prefix + " 开头：" + productId);
        }
        String rest = productId.substring(prefix.length());
        int dotIdx = rest.indexOf('.');
        if (dotIdx <= 0 || dotIdx == rest.length() - 1) {
            throw new IllegalArgumentException("苹果商品ID格式非法（应为 "
                    + prefix + "{monthly|yearly}.{套餐编码}）：" + productId);
        }
        String billingType = rest.substring(0, dotIdx);
        String planCode = rest.substring(dotIdx + 1);
        if (!"monthly".equals(billingType) && !"yearly".equals(billingType)) {
            throw new IllegalArgumentException("苹果商品ID计费周期段必须为 monthly/yearly：" + productId);
        }
        AfSubscriptionPlans plan = iAfSubscriptionPlansService.selectByPlanCode(planCode);
        if (plan == null) {
            throw new IllegalArgumentException("未配置的苹果商品（套餐编码 " + planCode + " 不存在）");
        }
        if (plan.getIsActive() == null || plan.getIsActive() != 1) {
            throw new IllegalArgumentException("苹果商品对应的套餐已停用：" + plan.getPlanCode());
        }

        String planId = String.valueOf(plan.getId());
        String addonId = null;
        String productName;
        BigDecimal amount = "Y".equals(paymentDebug) ? BigDecimal.valueOf(0.01)
                : "monthly".equals(billingType) ? plan.getMonthlyPriceYuan() : plan.getYearlyPriceYuan();
        productName = plan.getPlanNameCn();

        // 6. 落库 pending 订单，复用现有回调发货逻辑开通
        String orderNo = OrderNoGenerator.generate("apple");
        SysPaymentOrder order = new SysPaymentOrder();
        order.setOrderNo(orderNo);
        order.setAmount(amount);
        order.setPaymentType("apple");
        order.setProductName(productName);
        order.setQrCodeUrl(signedTransaction);
        order.setStatus("pending");
        order.setCreateTime(new Date());
        order.setUserInfo(clientUser.getUuid());
        order.setClientUserId(String.valueOf(clientUser.getUid()));
        order.setPlanId(planId);
        order.setAddonId(addonId);
        order.setBillingType(billingType);
        order.setQuantity(0);
        order.setAppleTransactionId(transactionId);
        order.setOriginalTransactionId(originalTransactionId);
        order.setAppleProductId(productId);
        paymentService.insert(order);

        boolean success = paymentServiceImpl.handlePaymentCallback(orderNo, "apple");
        if (!success) {
            throw new RuntimeException("苹果内购订单开通失败，orderNo=" + orderNo);
        }
        return paymentService.selectOne(orderNo);
    }

    @Override
    public boolean handleNotification(String signedPayload) {
        checkEnabled();
        try {
            JsonNode notification = AppleJwsVerifier.getInstance().verifyAndDecode(signedPayload);
            String type = notification.path("notificationType").asText("");
            String subtype = notification.path("subtype").asText("");
            JsonNode data = notification.path("data");
            String signedTransaction = data.path("signedTransaction").asText(null);

            log.info("收到苹果服务器通知：type={}, subtype={}", type, subtype);

            // 退款/撤销：撤订阅
            if ("REFUND".equals(type) || "REVOKE".equals(type)) {
                return handleRefundOrRevoke(type, signedTransaction);
            }

            // 各类"已付款"通知：兜底补单（本地已有则幂等跳过）
            if ("SUBSCRIBED".equals(type) || "DID_RENEW".equals(type)
                    || "OFFER_REDEEMED".equals(type) || isPurchaseType(type)) {
                return ensureTransactionFulfilled(signedTransaction);
            }

            // 其余（DID_CHANGE_RENEWAL_STATUS / GRACE_PERIOD_EXPIRED / EXPIRED 等）只记日志
            log.info("苹果通知 {} 无需发货处理，忽略", type);
            return true;
        } catch (Exception e) {
            log.error("苹果服务器通知处理失败", e);
            return false;
        }
    }

    private boolean isPurchaseType(String type) {
        return "ONE_TIME_CHARGE".equals(type);
    }

    /**
     * 退款/撤销：按原始交易号找订单标记 refunded，并撤销用户订阅
     */
    private boolean handleRefundOrRevoke(String type, String signedTransaction) {
        if (StringUtils.isEmpty(signedTransaction)) {
            log.warn("{} 通知缺少 signedTransaction", type);
            return false;
        }
        JsonNode tx = AppleJwsVerifier.getInstance().verifyAndDecode(signedTransaction);
        String originalTransactionId = tx.path("originalTransactionId").asText(null);
        String transactionId = tx.path("transactionId").asText(null);

        List<SysPaymentOrder> orders = new ArrayList<>();
        SysPaymentOrder byTx = paymentService.selectByAppleTransactionId(transactionId);
        if (byTx != null) {
            orders.add(byTx);
        }
        if (StringUtils.isNotEmpty(originalTransactionId)) {
            for (SysPaymentOrder o : paymentService.selectByOriginalTransactionId(originalTransactionId)) {
                if (orders.isEmpty() || orders.get(0).getId() == null
                        || !orders.get(0).getId().equals(o.getId())) {
                    orders.add(o);
                }
            }
        }
        if (orders.isEmpty()) {
            log.warn("{} 通知未找到对应订单，originalTransactionId={}", type, originalTransactionId);
            return true; // 已确认收到，避免苹果反复重发
        }
        for (SysPaymentOrder order : orders) {
            if (!"refunded".equals(order.getStatus())) {
                order.setStatus("refunded");
                order.setUpdateTime(new Date());
                paymentService.updateById(order);
            }
            // 撤销订阅（按订单归属用户）
            if (StringUtils.isNotEmpty(order.getClientUserId())) {
                try {
                    subscriptionService.cancelSubscription(Long.valueOf(order.getClientUserId()));
                } catch (Exception e) {
                    log.error("撤销订阅失败，clientUserId={}", order.getClientUserId(), e);
                }
            }
        }
        return true;
    }

    /**
     * 兜底补单：通知里的交易本地没有时，按 appAccountToken（= 客户端用户 uuid）识别用户并开通
     */
    private boolean ensureTransactionFulfilled(String signedTransaction) {
        if (StringUtils.isEmpty(signedTransaction)) {
            return false;
        }
        JsonNode tx = AppleJwsVerifier.getInstance().verifyAndDecode(signedTransaction);
        String transactionId = tx.path("transactionId").asText(null);
        if (paymentService.selectByAppleTransactionId(transactionId) != null) {
            return true; // 已处理
        }
        String appAccountToken = tx.path("appAccountToken").asText(null);
        ClientUser clientUser = StringUtils.isNotEmpty(appAccountToken)
                ? clientUserService.getClientUserByUuid(appAccountToken) : null;
        if (clientUser == null) {
            // 再尝试用原始交易号找到历史订单归属用户（续费场景）
            String originalTransactionId = tx.path("originalTransactionId").asText(null);
            if (StringUtils.isNotEmpty(originalTransactionId)) {
                List<SysPaymentOrder> history = paymentService.selectByOriginalTransactionId(originalTransactionId);
                if (!history.isEmpty() && StringUtils.isNotEmpty(history.get(0).getUserInfo())) {
                    clientUser = clientUserService.getClientUserByUuid(history.get(0).getUserInfo());
                }
            }
        }
        if (clientUser == null) {
            log.warn("苹果通知兜底补单无法识别用户（appAccountToken={}），transactionId={}", appAccountToken, transactionId);
            return false;
        }
        processTransaction(signedTransaction, clientUser);
        return true;
    }

    private void checkEnabled() {
        if (!appleIapConfig.isEnabled()) {
            throw new IllegalArgumentException("苹果内购未启用");
        }
    }
}
