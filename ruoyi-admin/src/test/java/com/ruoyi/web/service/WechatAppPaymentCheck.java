package com.ruoyi.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.web.config.WechatPayConfig;
import com.ruoyi.web.service.impl.PaymentServiceImpl;
import com.ruoyi.xmbj.domain.AfUserSubscriptions;
import com.ruoyi.xmbj.mapper.AfUserSubscriptionsMapper;
import com.ruoyi.xmbj.mapper.WechatAppFulfillmentMapper;
import com.ruoyi.xmbj.service.SubscriptionService;
import com.ruoyi.xmbj.service.WechatAppSubscriptionService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.model.TransactionAmount;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.yaml.snakeyaml.Yaml;

/** 无外部支付/数据库请求：运行main检查真实SDK验签解密、金额及重复回调。 */
public class WechatAppPaymentCheck {
    private static final String APP_ID = "wx3b1a7737f52a004b";
    private static final String API_KEY = "0123456789abcdef0123456789abcdef";
    private static int grants;
    private static int updates;
    private static SysPaymentOrder order;
    private static boolean failOrderUpdate;
    private static Long fulfilledSubscription;

    public static void main(String[] args) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        Config sdkConfig = new RSAPublicKeyConfig.Builder()
                .merchantId("merchant").privateKey(pair.getPrivate()).merchantSerialNumber("serial")
                .publicKey(pair.getPublic()).publicKeyId("PUB_KEY_ID_TEST").apiV3Key(API_KEY).build();
        WechatPayConfig config = new WechatPayConfig() {
            @Override public Config WechatPayConfig() { return sdkConfig; }
            @Override public String getMchId() { return "merchant"; }
        };
        ISysPaymentService orders = (ISysPaymentService) Proxy.newProxyInstance(
                ISysPaymentService.class.getClassLoader(), new Class<?>[]{ISysPaymentService.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("selectOne")) {
                        SysPaymentOrder copy = new SysPaymentOrder();
                        org.springframework.beans.BeanUtils.copyProperties(order, copy);
                        return copy;
                    }
                    if (method.getName().equals("updateById")) {
                        if (failOrderUpdate) throw new IllegalStateException("Simulated MySQL failure");
                        order = (SysPaymentOrder) arguments[0];
                        updates++;
                        return 1;
                    }
                    throw new AssertionError("Unexpected order operation: " + method.getName());
                });
        WechatAppPaymentService service = new WechatAppPaymentService();
        set(service, "config", config);
        set(service, "orders", orders);
        set(service, "appId", APP_ID);
        WechatAppSubscriptionService subscriptions = new WechatAppSubscriptionService();
        set(subscriptions, "fulfillmentMapper", new WechatAppFulfillmentMapper() {
            @Override public int claim(String orderNo) { return fulfilledSubscription == null ? 1 : 0; }
            @Override public Long subscriptionId(String orderNo) { return fulfilledSubscription; }
            @Override public int complete(String orderNo, Long subscriptionId) {
                fulfilledSubscription = subscriptionId;
                return 1;
            }
        });
        set(subscriptions, "subscriptionService", new SubscriptionService() {
            @Override public AfUserSubscriptions subscribe(Long userId, Long planId, String period) {
                grants++;
                return AfUserSubscriptions.builder().id(42L).build();
            }
        });
        set(service, "subscriptions", subscriptions);

        resetOrder();
        RequestParam valid = notification(pair, APP_ID, 990, false);
        service.handleNotification(valid);
        service.handleNotification(valid);
        check(grants == 1 && updates == 1, "Repeated callback granted membership twice");
        check("success".equals(order.getStatus()), "Payment was not completed");
        check("42".equals(order.getClientSubscriptionId()), "Subscription ID not recorded");

        resetOrder();
        rejects(() -> service.handleNotification(notification(pair, APP_ID, 990, true)));
        rejects(() -> service.handleNotification(notification(pair, "web-app-id", 990, false)));
        rejects(() -> service.handleNotification(notification(pair, APP_ID, 1, false)));
        check(grants == 1 && updates == 1, "Rejected callback changed membership");

        order.setPaymentType("wechat_native");
        rejects(() -> service.handleNotification(valid));
        order.setPaymentType("wechat_jsapi");
        rejects(() -> service.handleNotification(valid));
        resetOrder();
        PaymentServiceImpl legacy = new PaymentServiceImpl();
        set(legacy, "paymentService", orders);
        check(!legacy.handlePaymentCallback(order.getOrderNo(), "wechat"),
                "Legacy callback must not fulfill App orders");
        check(grants == 1 && updates == 1, "Legacy callback changed App membership");

        fulfilledSubscription = null;
        failOrderUpdate = true;
        rejects(() -> service.handleNotification(valid));
        check(grants == 2 && "pending".equals(order.getStatus()), "Failure setup incorrect");
        failOrderUpdate = false;
        service.handleNotification(valid);
        check(grants == 2 && updates == 2 && "success".equals(order.getStatus()),
                "Cross-database retry granted membership twice");
        checkOrderStatuses(service);
        if (Boolean.getBoolean("payment.check.localDatabase")) checkLocalSubscriptionId();
        System.out.println("WeChat App payment checks passed (signature, decryption, amount, AppID, isolation, duplicate, retry, unpaid/closed status).");
    }

    private static void checkOrderStatuses(WechatAppPaymentService service) throws Exception {
        resetOrder();
        int previousGrants = grants;
        int previousUpdates = updates;
        Transaction transaction = new Transaction();
        transaction.setAppid(APP_ID);
        transaction.setMchid("merchant");
        transaction.setOutTradeNo(order.getOrderNo());
        for (Transaction.TradeStateEnum state : new Transaction.TradeStateEnum[]{
                Transaction.TradeStateEnum.NOTPAY, Transaction.TradeStateEnum.USERPAYING,
                Transaction.TradeStateEnum.CLOSED, Transaction.TradeStateEnum.REVOKED,
                Transaction.TradeStateEnum.PAYERROR}) {
            transaction.setTradeState(state);
            String expected = state == Transaction.TradeStateEnum.NOTPAY
                    || state == Transaction.TradeStateEnum.USERPAYING ? "pending" : "failed";
            check(expected.equals(service.checkStatus(order, transaction)),
                    "Status response without payment fields rejected: " + state);
        }
        transaction.setTradeState(Transaction.TradeStateEnum.NOTPAY);
        transaction.setAppid("web-app-id");
        rejects(() -> service.checkStatus(order, transaction));
        transaction.setAppid(APP_ID);
        transaction.setMchid("another-merchant");
        rejects(() -> service.checkStatus(order, transaction));
        transaction.setMchid("merchant");
        transaction.setOutTradeNo("another-order");
        rejects(() -> service.checkStatus(order, transaction));
        transaction.setOutTradeNo(order.getOrderNo());
        transaction.setTradeType(Transaction.TradeTypeEnum.JSAPI);
        rejects(() -> service.checkStatus(order, transaction));
        transaction.setTradeType(Transaction.TradeTypeEnum.APP);
        TransactionAmount amount = new TransactionAmount();
        amount.setTotal(990);
        transaction.setAmount(amount);
        check("pending".equals(service.checkStatus(order, transaction)),
                "Unpaid response without currency rejected");

        transaction.setTradeState(Transaction.TradeStateEnum.SUCCESS);
        transaction.setTransactionId("wechat-query-transaction");
        transaction.setSuccessTime("2026-10-20T12:00:00+08:00");
        rejects(() -> service.checkStatus(order, transaction));
        amount.setCurrency("CNY");
        transaction.setAmount(null);
        rejects(() -> service.checkStatus(order, transaction));
        transaction.setAmount(amount);
        amount.setTotal(1);
        rejects(() -> service.checkStatus(order, transaction));
        amount.setTotal(990);
        transaction.setTradeType(null);
        rejects(() -> service.checkStatus(order, transaction));
        check(grants == previousGrants && updates == previousUpdates,
                "Unpaid/closed or invalid successful query changed membership");

        transaction.setTradeType(Transaction.TradeTypeEnum.APP);
        fulfilledSubscription = null;
        check("success".equals(service.checkStatus(order, transaction)), "Valid successful query rejected");
        check("success".equals(service.checkStatus(order)), "Completed order status changed");
        check(grants == previousGrants + 1 && updates == previousUpdates + 1,
                "Successful status query granted membership more than once");
    }

    /** Opt-in check against the local database; the inserted subscription is always rolled back. */
    private static void checkLocalSubscriptionId() throws Exception {
        Map<?, ?> config;
        try (InputStream input = Files.newInputStream(Path.of("ruoyi-admin/src/main/resources/application-mydev.yml"))) {
            config = new Yaml().load(input);
        }
        Map<?, ?> spring = (Map<?, ?>) config.get("spring");
        Map<?, ?> datasource = (Map<?, ?>) spring.get("datasource");
        Map<?, ?> slave = (Map<?, ?>) ((Map<?, ?>) datasource.get("druid")).get("slave");
        String url = (String) slave.get("url");
        check("jdbc:postgresql://localhost:5432/xiaomabiji".equals(url), "Database check requires local PostgreSQL");
        UnpooledDataSource source = new UnpooledDataSource("org.postgresql.Driver", url,
                slave.get("username").toString(), slave.get("password").toString());
        Configuration configuration = new Configuration(new Environment("local-check", new JdbcTransactionFactory(), source));
        String mapper = "ruoyi-system/src/main/resources/mapper/xmbj/AfUserSubscriptionsMapper.xml";
        try (InputStream input = Files.newInputStream(Path.of(mapper))) {
            new XMLMapperBuilder(input, configuration, mapper, configuration.getSqlFragments()).parse();
        }
        Long insertedId = null;
        try (SqlSession session = new SqlSessionFactoryBuilder().build(configuration).openSession(false)) {
            try {
                AfUserSubscriptions subscription;
                try (PreparedStatement select = session.getConnection().prepareStatement(
                        "SELECT uid, plan_id FROM af_user_subscriptions ORDER BY id LIMIT 1");
                     ResultSet rows = select.executeQuery()) {
                    check(rows.next(), "Local database needs an existing test subscription");
                    Date now = new Date();
                    subscription = AfUserSubscriptions.builder().uid(rows.getLong("uid")).planId(rows.getLong("plan_id"))
                            .billingType("monthly").status("canceled").startDate(now).endDate(now)
                            .createdAt(now).updatedAt(now).build();
                }
                session.getMapper(AfUserSubscriptionsMapper.class).insert(subscription);
                insertedId = subscription.getId();
                check(insertedId != null, "Subscription insert did not return its generated ID");
                try (PreparedStatement select = session.getConnection().prepareStatement(
                        "SELECT id FROM af_user_subscriptions WHERE id = ?")) {
                    select.setLong(1, insertedId);
                    try (ResultSet rows = select.executeQuery()) {
                        check(rows.next(), "Returned subscription ID does not match an inserted record");
                    }
                }
            } finally {
                session.rollback(true);
            }
            try (PreparedStatement select = session.getConnection().prepareStatement(
                    "SELECT id FROM af_user_subscriptions WHERE id = ?")) {
                select.setLong(1, insertedId);
                try (ResultSet rows = select.executeQuery()) {
                    check(!rows.next(), "Database check left a subscription behind");
                }
            }
        }
        System.out.println("Local PostgreSQL subscription ID check passed (real mapper insert, generated ID, rollback).");
    }

    private static void resetOrder() {
        order = new SysPaymentOrder();
        order.setOrderNo("0123456789abcdef0123456789abcdef");
        order.setPaymentType("wechat_app");
        order.setStatus("pending");
        order.setAmount(new BigDecimal("9.90"));
        order.setClientUserId("1");
        order.setPlanId("2");
        order.setBillingType("monthly");
    }

    private static RequestParam notification(KeyPair pair, String appId, int total, boolean tamper) throws Exception {
        JSONObject transaction = new JSONObject();
        transaction.put("appid", appId);
        transaction.put("mchid", "merchant");
        transaction.put("out_trade_no", order.getOrderNo());
        transaction.put("transaction_id", "wechat-transaction");
        transaction.put("trade_type", "APP");
        transaction.put("trade_state", "SUCCESS");
        transaction.put("success_time", "2026-10-20T12:00:00+08:00");
        JSONObject amount = new JSONObject();
        amount.put("total", total);
        amount.put("currency", "CNY");
        transaction.put("amount", amount);
        String nonce = "123456789012";
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(API_KEY.getBytes(StandardCharsets.UTF_8), "AES"),
                new GCMParameterSpec(128, nonce.getBytes(StandardCharsets.UTF_8)));
        cipher.updateAAD("transaction".getBytes(StandardCharsets.UTF_8));
        JSONObject resource = new JSONObject();
        resource.put("algorithm", "AEAD_AES_256_GCM");
        resource.put("nonce", nonce);
        resource.put("associated_data", "transaction");
        resource.put("ciphertext", Base64.getEncoder().encodeToString(
                cipher.doFinal(transaction.toJSONString().getBytes(StandardCharsets.UTF_8))));
        JSONObject body = new JSONObject();
        body.put("event_type", "TRANSACTION.SUCCESS");
        body.put("resource_type", "encrypt-resource");
        body.put("resource", resource);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String payload = body.toJSONString();
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(pair.getPrivate());
        signature.update((timestamp + "\n" + nonce + "\n" + payload + "\n").getBytes(StandardCharsets.UTF_8));
        String signed = Base64.getEncoder().encodeToString(signature.sign());
        return new RequestParam.Builder().serialNumber("PUB_KEY_ID_TEST").timestamp(timestamp)
                .nonce(nonce).signature(signed).body(tamper ? payload + " " : payload).build();
    }

    private static void set(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static void rejects(CheckedAction action) throws Exception {
        try { action.run(); } catch (RuntimeException expected) { return; }
        throw new AssertionError("Invalid payment notification accepted");
    }

    private interface CheckedAction { void run() throws Exception; }
}
