package com.ruoyi.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.internal.util.file.IOUtils;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.domain.vo.PaymentOrderVo;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.web.config.RestTemplateConfig;
import com.ruoyi.system.domain.vo.PaymentResult;
import com.ruoyi.web.config.WechatPayConfig;
import com.ruoyi.web.config.XmAlipayConfig;
import com.ruoyi.web.service.PaymentService;
import com.ruoyi.web.util.OrderNoGenerator;
import com.ruoyi.xmbj.api.protocol.subscription.PurchaseAddonRequest;
import com.ruoyi.xmbj.api.protocol.subscription.SubscribeRequest;
import com.ruoyi.xmbj.api.service.PonynotesService;
import com.ruoyi.xmbj.domain.*;
import com.ruoyi.xmbj.service.IAfSubscriptionPlansService;
import com.ruoyi.xmbj.service.SubscriptionService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;

import com.wechat.pay.java.service.partnerpayments.nativepay.model.QueryOrderByOutTradeNoRequest;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import com.wechat.pay.java.service.payments.h5.H5Service;
import com.wechat.pay.java.service.payments.h5.model.PrepayRequest;
import com.wechat.pay.java.service.payments.h5.model.Amount;
import com.wechat.pay.java.service.payments.h5.model.PrepayResponse;
import com.wechat.pay.java.service.payments.h5.model.SceneInfo;
//import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
//import com.wechat.pay.java.service.payments.nativepay.model.Amount;

//import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
//import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
//import com.wechat.pay.java.service.payments.nativepay.model.QueryOrderByOutTradeNoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final String REDIS_KEY_JSAPI_TICKET = "wx_jsapi_ticket";
    private static final Object TICKET_EXPIRE_SEC = 5000;

    @Autowired
    private com.wechat.pay.java.service.partnerpayments.nativepay.NativePayService wechatNativePayService;
    @Autowired
    private WechatPayConfig wechatPayConfig; // 微信支付配置
    @Autowired
    private AlipayClient alipayClient; // 支付宝客户端
    @Autowired
    private XmAlipayConfig alipayConfig; // 支付宝配置

    @Autowired
    private ISysPaymentService paymentService; // 项目系统订单服务

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PonynotesService ponynotesService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private IAfSubscriptionPlansService iAfSubscriptionPlansService;


    /**
     * 创建支付订单并生成二维码（微信/支付宝真实调用）
     */
    @Override
    public PaymentResult createPayment( String paymentType, SysUser sysUser, ClientUser clientUser,
                                       String openid, String url, String planId, String billingType, String addonId,
                                       HttpServletRequest httpServletRequest) {

        AfSubscriptionPlans plan = iAfSubscriptionPlansService.selectAfSubscriptionPlansById(Long.valueOf(planId));
        if(plan == null) throw new IllegalArgumentException("参数非法,planId错误");

        BigDecimal amount = "0".equals(billingType) ? plan.getMonthlyPriceYuan():  plan.getYearlyPriceYuan();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("支付金额非法：{}", amount);
            throw new IllegalArgumentException("支付金额必须大于0");
        }
//        String userInfo = sysUser.getUserName();
//        if (!StringUtils.hasText(userInfo)) {
//            throw new RuntimeException("用户信息不能为空");
//        }
        // 1. 生成唯一订单号orderNoGenerator
        // String orderNo = generateOrderNo(paymentType);
        String orderNo = OrderNoGenerator.generate(paymentType);
        String payInfo = "";

        // 2. 根据支付方式调用对应SDK生成二维码
        if ("wechat".equals(paymentType)) {
            // payUrl = createWechatH5PayUrl(orderNo, amount, httpServletRequest);
            if (StringUtils.isEmpty(openid)) {
                throw new IllegalArgumentException("JSAPI支付需要用户openid");
            }
            payInfo = createWechatJsapiPay(orderNo, amount, openid, url);

        } else if ("wechat_jsapi".equals(paymentType)) {
            // 校验openid（JSAPI必需）
            if (StringUtils.isEmpty(openid)) {
                throw new IllegalArgumentException("JSAPI支付需要用户openid");
            }
            payInfo = createWechatJsapiPay(orderNo, amount, openid, url);
        } else if ("wechat_native".equals(paymentType)) {
            // 校验openid（JSAPI必需）
            if (StringUtils.isEmpty(openid)) {
                throw new IllegalArgumentException("JSAPI支付需要用户openid");
            }
            payInfo = createWechatQrCode(orderNo, amount,httpServletRequest);
        } else if ("alipay".equals(paymentType)) {
            payInfo = createAlipayPagePayUrl(orderNo, amount,clientUser,plan);
        } else if ("alipay_qr".equals(paymentType)) {
            payInfo = createAlipayQrCodePayUrl(orderNo, amount,clientUser.getUuid());
        } else {
            throw new IllegalArgumentException("不支持的支付方式：" + paymentType);
        }
        // tokenService.getUserInfoFromToken(httpServletRequest).toString();

        // 3. 保存订单
        SysPaymentOrder order = new SysPaymentOrder();
        order.setOrderNo(orderNo);
        order.setAmount(amount);
        order.setPaymentType(paymentType);
        order.setProductName(plan.getPlanNameCn());

        order.setQrCodeUrl(payInfo);
        order.setStatus("pending"); // 待支付
        order.setCreateTime(new Date());
        order.setUserInfo(clientUser.getUuid());
        if(sysUser != null) {
            order.setUserId(sysUser.getUserId().toString());
        }
        order.setClientUserId(String.valueOf(clientUser.getUid()));
        order.setPlanId(planId);
        order.setAddonId(addonId);
        order.setBillingType(billingType);
        order.setQuantity(0);
        paymentService.insert(order);

        // 4. 返回二维码信息
        PaymentResult vo = new PaymentResult();
        vo.setOrderNo(orderNo);
        vo.setPayUrl(payInfo);
        vo.setExpireTime(DateUtils.addMinutes(new Date(), 15)); // 15分钟过期
        return vo;

    }

    /**
     * 微信JSAPI支付：生成调起参数（给前端用）
     */
    private String createWechatJsapiPay(String orderNo, BigDecimal amount, String openid, String url) {
        try {
            // 构建JSAPI支付请求参数
            com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest request = new com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest();
            request.setOutTradeNo(orderNo);
            request.setDescription("会员充值-" + orderNo);
            request.setNotifyUrl(wechatPayConfig.getNotifyUrl());
            request.setAppid(wechatPayConfig.getAppId());
            request.setMchid(wechatPayConfig.getMchId());

            // 金额（分）
            com.wechat.pay.java.service.payments.jsapi.model.Amount amountObj = new com.wechat.pay.java.service.payments.jsapi.model.Amount();
            amountObj.setTotal(amount.multiply(new BigDecimal(100)).intValue());
            request.setAmount(amountObj);

            // JSAPI必需：用户openid
            com.wechat.pay.java.service.payments.jsapi.model.Payer payer = new com.wechat.pay.java.service.payments.jsapi.model.Payer();
            payer.setOpenid(openid);
            request.setPayer(payer);
            // 调用微信JSAPI支付接口
            com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse response = wechatPayConfig.jsapiPayService()
                    .prepay(request);
            // 返回前端调起支付所需的参数（JSON格式）
            JSONObject jsApiParams = new JSONObject();
            jsApiParams.put("appId", wechatPayConfig.getAppId());
            jsApiParams.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            jsApiParams.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));
            jsApiParams.put("package", "prepay_id=" + response.getPrepayId()); // 关键：prepay_id
            jsApiParams.put("signType", "RSA");
            // 生成签名（前端调起时需要）
            String sign = generateJsapiSign(jsApiParams, wechatPayConfig.getPrivateKeyPath());
            jsApiParams.put("paySign", sign);

            return jsApiParams.toString();
        } catch (Exception e) {
            log.error("微信JSAPI支付创建失败", e);
            throw new RuntimeException("JSAPI支付订单创建失败：" + e.getMessage());
        }
    }

    /**
     * 生成JSAPI支付签名（供前端调起使用）
     */
    private String generateJsapiSign(JSONObject params, String privateKeyPath) throws Exception {
        // 1. 读取商户私钥
        Resource resource = new ClassPathResource(privateKeyPath);
        String privateKeyPEM = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);

        // 2. 按规则拼接签名串（appId、timeStamp、nonceStr、package）
        String signStr = String.format("appId=%s&timeStamp=%s&nonceStr=%s&package=%s",
                params.getString("appId"),
                params.getString("timeStamp"),
                params.getString("nonceStr"),
                params.getString("package"));

        // 3. 使用私钥签名
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // 去除所有空白字符

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(signStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * 微信支付：生成Native支付二维码
     */
    private String createWechatQrCode(String orderNo, BigDecimal amount, HttpServletRequest httpServletRequest) {
        // 构建请求参数

        com.wechat.pay.java.service.partnerpayments.nativepay.model.PrepayRequest request = new com.wechat.pay.java.service.partnerpayments.nativepay.model.PrepayRequest();
        request.setOutTradeNo(orderNo); // 商户订单号
        request.setDescription("会员充值-" + orderNo); // 订单描述
        request.setNotifyUrl(wechatPayConfig.getNotifyUrl()); // 回调地址
        request.setSpAppid(wechatPayConfig.getAppId()); // 从配置中获取服务商appid
        request.setSpMchid(wechatPayConfig.getMchId());
        // 子商户参数 (新增)
        // request.setSubMchid(wechatPayConfig.getSubMchid()); // 从配置中获取子商户号
        // 金额（单位：分）
        com.wechat.pay.java.service.partnerpayments.nativepay.model.Amount amountObj = new com.wechat.pay.java.service.partnerpayments.nativepay.model.Amount();
        amountObj.setTotal(amount.multiply(new BigDecimal(100)).intValue()); // 元转分
        request.setAmount(amountObj);

        // 调用微信支付SDK生成二维码
        com.wechat.pay.java.service.partnerpayments.nativepay.model.PrepayResponse response = wechatNativePayService
                .prepay(request);
        return response.getCodeUrl(); // 返回微信支付二维码链接
    }

    // 微信H5支付链接生成
    public String createWechatH5PayUrl(String orderNo, BigDecimal amount, HttpServletRequest httpServletRequest) {
        String paymentClientIp = getClientIp(httpServletRequest);
        if (StringUtils.isEmpty(paymentClientIp)) {
            paymentClientIp = "127.0.0.1"; // 兜底IP
        }
        // 1. 构建请求参数
        PrepayRequest request = new PrepayRequest();
        request.setOutTradeNo(orderNo); // 商户订单号
        request.setAppid(wechatPayConfig.getAppId());
        request.setMchid(wechatPayConfig.getMchId());
        request.setDescription("会员充值"); // 商品描述
        request.setNotifyUrl(wechatPayConfig.getNotifyUrl()); // 支付结果回调地址（后端接口）

        Amount amountObj = new Amount();
        amountObj.setTotal(amount.multiply(new BigDecimal(100)).intValue()); // 元转分
        request.setAmount(amountObj);

        // 设置场景信息（用户IP等）
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setPayerClientIp(paymentClientIp); // 用户终端IP
        request.setSceneInfo(sceneInfo);

        // 3. 调用微信H5支付接口
        H5Service h5Service = new H5Service.Builder()
                .config(wechatPayConfig.WechatPayConfig()) // 使用配置类中的Config对象
                .build();
        // H5Service h5Service = new H5Service.Builder().build();
        PrepayResponse response = h5Service.prepay(request);
        String payUrl = response.getH5Url(); // 支付跳转链接
        return payUrl;
    }

    /**
     * 支付宝：生成预下单二维码
     */
    private String createAlipayQrCode(String orderNo, BigDecimal amount) throws Exception {
        // 构建请求参数
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl()); // 回调地址

        // 业务参数（JSON格式）
        String bizContent = "{" +
                "\"out_trade_no\":\"" + orderNo + "\"," +
                "\"total_amount\":\"" + amount.setScale(2) + "\"," + // 金额（元，保留2位小数）
                "\"subject\":\"会员充值-" + orderNo + "\"," + // 订单标题
                "\"timeout_express\":\"15m\"" + // 过期时间15分钟
                "}";
        request.setBizContent(bizContent);
        // 最多重试3次
        int maxRetries = 3;
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                AlipayTradePrecreateResponse response = alipayClient.execute(request);
                if (response.isSuccess()) {
                    return response.getQrCode();
                } else {
                    throw new RuntimeException("支付宝二维码生成失败：" + response.getMsg());
                }
            } catch (AlipayApiException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    throw e; // 达到最大重试次数，抛出异常
                }
                log.warn("支付宝接口调用失败，将进行第{}次重试: {}", retryCount + 1, e.getMessage());
                Thread.sleep(1000 * retryCount); // 指数退避重试
            }
        }
        // // 调用支付宝SDK生成二维码
        // AlipayTradePrecreateResponse response = alipayClient.execute(request);
        // if (!response.isSuccess()) {
        // throw new RuntimeException("支付宝二维码生成失败：" + response.getMsg());
        // }
        // return response.getQrCode(); // 返回支付宝二维码链接
        throw new RuntimeException("支付宝二维码生成失败，已达到最大重试次数");
    }

    // 支付宝网页支付链接生成
    public String createAlipayPagePayUrl(String orderNo, BigDecimal amount,ClientUser clientUser,AfSubscriptionPlans plan) {
        // 1. 构建请求参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipayConfig.getReturnUrl()); // 支付成功后前端跳转地址（如：https://xxx.com/pay/result）
        request.setNotifyUrl(alipayConfig.getNotifyUrl()); // 支付结果回调地址（后端接口）

        // 2. 业务参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo); // 商户订单号
        bizContent.put("total_amount", amount.setScale(2)); // 金额（元）
        bizContent.put("subject", "小马笔记-"+plan.getPlanNameCn()); // 商品标题
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 电脑网站支付标识
        request.setBizContent(bizContent.toString());

        // 3. 调用支付宝接口，获取支付表单
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                return response.getBody(); // 返回表单HTML
            } else {
                throw new RuntimeException("支付宝网页支付创建失败：" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            log.error("支付宝接口调用异常", e);
            throw new RuntimeException("支付宝支付创建失败");
        }
    }


    // 支付宝网页支付链接生成
    public String createAlipayQrCodePayUrl(String orderNo, BigDecimal amount,String userInfo) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();

        // 设置商户订单号
        model.setOutTradeNo(orderNo);

        // 设置订单总金额
        model.setTotalAmount(String.valueOf(amount.setScale(2, BigDecimal.ROUND_UP)));

        // 设置订单标题
        model.setSubject("会员充值");

        // 设置产品码
        model.setProductCode("QR_CODE_OFFLINE");

        // 设置业务扩展参数
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(userInfo);
        model.setExtendParams(extendParams);


        request.setBizModel(model);

        // 3. 调用支付宝接口，获取支付表单
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("调用成功");
                return response.getQrCode();
            } else {
                System.out.println("调用失败");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                // System.out.println(diagnosisUrl);

                throw new RuntimeException("支付宝接口调用失败");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝接口调用异常", e);
            throw new RuntimeException("支付宝支付创建失败");
        }
    }

    private AlipayConfig getAlipayConfig() {
        String privateKey  = alipayConfig.getPrivateKey();
        String alipayPublicKey = alipayConfig.getPublicKey();
        AlipayConfig c = new AlipayConfig();
        c.setServerUrl("https://openapi.alipay.com/gateway.do");
        c.setAppId(alipayConfig.getAppId());
        c.setPrivateKey(privateKey);
        c.setFormat("json");
        c.setAlipayPublicKey(alipayPublicKey);
        c.setCharset("UTF-8");
        c.setSignType("RSA2");
        return c;

    }

    /**
     * 查询支付状态（真实调用支付平台接口）
     */

    @Override
    public String checkPaymentStatus(String orderNo) {
        // 1. 查询本地订单
        SysPaymentOrder order = paymentService.selectOne(orderNo);
        if (order == null) {
            return "invalid"; // 订单不存在
        }

        // 2. 已支付状态直接返回
        if ("success".equals(order.getStatus())) {
            return "success";
        }

        // 3. 调用支付平台接口查询最新状态
        try {
            if ("wechat".equals(order.getPaymentType())) {
                // 微信支付查询
                Config config = new RSAAutoCertificateConfig.Builder()
                        .merchantId(wechatPayConfig.getMchId())
                        .privateKeyFromPath(wechatPayConfig.getPrivateKeyPath())
                        .merchantSerialNumber(wechatPayConfig.getMchSerialNo())
                        .apiV3Key(wechatPayConfig.getApiV3Key())
                        .build();

                // NativePayService nativePayService = new
                // NativePayService.Builder().config(config).build();
                QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
                request.setOutTradeNo(orderNo);
                Transaction response = wechatNativePayService.queryOrderByOutTradeNo(request);

                if ("SUCCESS".equals(response.getTradeState())) {
                    order.setStatus("success");
                    order.setPayTime(new Date());
                    paymentService.updateById(order);
                    return "success";
                } else if ("CLOSED".equals(response.getTradeState())) {
                    order.setStatus("failed");
                    paymentService.updateById(order);
                    return "failed";
                } else if ("REVOKED".equals(response.getTradeState())) {
                    order.setStatus("failed");
                    paymentService.updateById(order);
                    return "failed";
                } else {
                    return "pending";
                }
            } else if ("alipay".equals(order.getPaymentType())) {
                // 支付宝支付查询
                AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
                request.setBizContent("{" +
                        "\"out_trade_no\":\"" + orderNo + "\"" +
                        "}");
                AlipayTradeQueryResponse response = alipayClient.execute(request);
                if (response.isSuccess()) {
                    // 支付宝交易状态：TRADE_SUCCESS=支付成功，TRADE_CLOSED=交易关闭
                    if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
                        order.setStatus("success");
                        order.setPayTime(new Date());
                        paymentService.updateById(order);
                        return "success";
                    } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {
                        order.setStatus("failed");
                        paymentService.updateById(order);
                        return "failed";
                    }
                }
            }
        } catch (Exception e) {
            log.error("查询支付状态失败，订单号：{}", orderNo, e);
        }

        // 4. 未支付或查询失败，返回当前本地状态
        return order.getStatus();
    }

    /**
     * 查询支付状态（调用真实支付平台接口）
     */
    // @Override
    // public String checkPaymentStatus(String orderNo) {
    // // 1. 查询本地订单
    // PaymentOrder order = paymentService.selectOne(orderNo);
    // if (order == null) {
    // return "invalid"; // 订单不存在
    // }
    //
    // // 2. 已支付状态直接返回
    // if ("success".equals(order.getStatus())) {
    // return "success";
    // }
    //
    // // 3. 调用支付平台接口查询最新状态
    // try {
    // if ("wechat".equals(order.getPaymentType())) {
    // // 微信支付查询（通过商户订单号）
    // QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
    // request.setOutTradeNo(orderNo);
    // Transaction response =
    // wechatNativePayService.queryOrderByOutTradeNo(request);
    // // 微信支付状态：SUCCESS=支付成功，REFUND=已退款，NOTPAY=未支付，CLOSED=已关闭，REVOKED=已撤销（付款码支付）
    // if ("NOTPAY".equals(response.getTradeState())) {
    // // 检查是否已过期
    // if (order.getCreateTime().before(DateUtils.addMinutes(new Date(), -15))) {
    // order.setStatus("expired");
    // paymentService.updateById(order);
    // return "expired";
    // }
    // return "pending"; // 仍在有效期内，未支付
    // }else if ("SUCCESS".equals(response.getTradeState())) {
    // // 更新本地状态为成功
    // order.setStatus("success");
    // order.setPayTime(new Date());
    // paymentService.updateById(order);
    // return "success";
    // } else if ("CLOSED".equals(response.getTradeState()) ||
    // "REVOKED".equals(response.getTradeState())) {
    // order.setStatus("failed");
    // paymentService.updateById(order);
    // return "failed";
    // }
    // } else if ("alipay".equals(order.getPaymentType())) {
    // // 支付宝支付查询
    // AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
    // request.setBizContent("{" +
    // "\"out_trade_no\":\"" + orderNo + "\"" +
    // "}");
    // AlipayTradeQueryResponse response = alipayClient.execute(request);
    // if (response.isSuccess()) {
    // // 支付宝交易状态：TRADE_SUCCESS=支付成功，TRADE_CLOSED=交易关闭
    // if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
    // order.setStatus("success");
    // order.setPayTime(new Date());
    // paymentService.updateById(order);
    // return "success";
    // } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {
    // order.setStatus("failed");
    // paymentService.updateById(order);
    // return "failed";
    // }
    // }
    // }
    // } catch (Exception e) {
    // log.error("查询支付状态失败，订单号：{}", orderNo, e);
    // }
    //
    // // 4. 未支付或查询失败，返回当前本地状态
    // return order.getStatus();
    // }

    /**
     * 处理支付回调（更新订单状态）
     */
    @Override
    public boolean handlePaymentCallback(String orderNo, String paymentType) {
        SysPaymentOrder order = paymentService.selectOne(orderNo);
        if (order == null || !"pending".equals(order.getStatus())) {
            return false;
        }
        boolean result = false;
        // 更新订单状态为已支付
        order.setStatus("success");
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());

        // SubscribeRequest subscribeRequest = new SubscribeRequest();
        // subscribeRequest.setBillingType(order.getBillingType());
        // subscribeRequest.setPlanId(Long.valueOf(order.getPlanId()));
        // ponynotesService.subscribe(subscribeRequest);
        //
        // //创建补充包
        // PurchaseAddonRequest addon = new PurchaseAddonRequest();
        // addon.setAddonId(Long.valueOf(order.getAddonId()));
        // addon.setQuantity(Integer.parseInt("1"));
        // ponynotesService.purchaseAddon(addon);
        if (!StringUtils.isEmpty(order.getPlanId())) {
            AfUserSubscriptions userSubscription = subscriptionService.subscribe(Long.valueOf(order.getClientUserId()),
                    Long.valueOf(order.getPlanId()), order.getBillingType());
            order.setClientSubscriptionId(String.valueOf(userSubscription.getId()));
        }
        if (!StringUtils.isEmpty(order.getAddonId())) {
            AfUserAddons userAddon = subscriptionService.purchaseAddon(Long.valueOf(order.getClientUserId()),
                    Long.valueOf(order.getAddonId()), 1);
            order.setClientUserAddonId(String.valueOf(userAddon.getId()));
        }
        try {
            paymentService.updateById(order);
            result = true;
        } catch (Exception e) {
            log.error("更新订单状态异常",e);
        }
        return result;
    }

    @Override
    public boolean verifySign(String paymentType, String orderNo, Map<String, String> sign) {
        Boolean verifySuccess = false;
        try {
            // 1. 支付宝验签
            if ("alipay".equals(paymentType)) {
                verifySuccess = alipayConfig.verifySign(sign);
            }
            // 2. 微信支付验签（微信回调参数为XML，需先解析）
            else if ("wechat".equals(paymentType)) {
                // 解析微信XML回调参数
                Map<String, String> wechatParams = sign;
                // 微信验签（使用官方SDK）
                verifySuccess = true;
                // verifySuccess = wechatPayConfig.verifySign(wechatParams);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("支付回调验签失败", e);
            return false;
        } finally {
            return verifySuccess;
        }
    }

    @Override
    public Map<String, String> getWechatParams(HttpServletRequest request) {
        String body = null;
        try {
            body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        String nonce = request.getHeader("Wechatpay-Nonce");
        String signature = request.getHeader("Wechatpay-Signature");
        String serial = request.getHeader("Wechatpay-Serial");

        // 2. 验签
        Map<String, String> wechatParams = new HashMap<>();
        wechatParams.put("body", body);
        wechatParams.put("timestamp", timestamp);
        wechatParams.put("nonce", nonce);
        wechatParams.put("signature", signature);
        wechatParams.put("serial", serial);
        // wechatParams.put("apiV3Key", wechatPayConfig.getApiV3Key()); // 传入APIv3密钥
        return wechatParams;
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo(String paymentType) {
        return paymentType + "_" + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().substring(0, 8);
    }

    // 新增获取客户端IP方法
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP情况，取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    @Override
    public SysPaymentOrder getPaymentOrder(String TradeNo) {
        // TODO Auto-generated method stub
        return paymentService.getPaymentOrder(TradeNo);
    }

    @Override
    public JSONObject getOpenid(String code) {
        // 调用微信 API 换取 openid
        String appid = wechatPayConfig.getAppId();
        String secret = wechatPayConfig.getAppSecret();
        // String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
        // appid + "&secret=" + secret + "&code=" + code +
        // "&grant_type=authorization_code";
        String url = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/oauth2/access_token")
                .queryParam("appid", appid)
                .queryParam("secret", secret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .toUriString();
        try {
            // getForObject 会自动发起 GET 请求并将响应体转换成指定的类型（这里是 String）
            String responseBody = restTemplateConfig.restTemplate().getForObject(url, String.class);
            return JSONObject.parseObject(responseBody);
        } catch (Exception e) {
            // 异常处理
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "Exception occurred: " + e.getMessage());
            return errorJson;
        }
        // String result = HttpUtil.get(url);
        // JSONObject json = JSONObject.parseObject(result);
        // return json;
    }

    @Override
    public Map<String, String> getJsApiConfig(String url) {
        // 1. 生成随机字符串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 2. 生成时间戳（秒级）
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        // 3. 拼接签名串（jsapi_ticket=xxx&noncestr=xxx&timestamp=xxx&url=xxx）
        // String jsapiTicket = getJsApiTicket(); // 从微信接口获取jsapi_ticket（需缓存）
        String jsapiTicket = "";
        String signStr = String.format("jsapi_ticket=%s&noncestr=%s×tamp=%s&url=%s",
                jsapiTicket, nonceStr, timestamp, url);
        // 4. 用商户私钥签名
        // String signature = generateJsapiSign(signStr); // 实现RSA签名逻辑
        String signature = "";
        Map<String, String> config = new HashMap<>();
        config.put("appId", wechatPayConfig.getAppId());
        config.put("timestamp", timestamp);
        config.put("nonceStr", nonceStr);
        config.put("signature", signature);
        return config;
    }

    @Override
    public List<PaymentOrderVo> userPaymentOrders(PaymentOrderVo paymentOrderVo) {
        return paymentService.userPaymentOrders(paymentOrderVo);
    }

    @Override
    public List<PaymentOrderVo> serPaymentOrders(PaymentOrderVo paymentOrderVo) {
        List<PaymentOrderVo> list = paymentService.userPaymentOrders(paymentOrderVo);
        for (PaymentOrderVo item : list) {
            // ClientSubscriptionId; // 订阅订单ID
            // ClientUserAddonId; // 补充包订单ID
            if (item.getPlanId() != null) {
                AfUserSubscriptions afUserSubscriptions = subscriptionService
                        .selectUserSubscription(item.getClientSubscriptionId());
                item.setStartDate(afUserSubscriptions.getStartDate());
                item.setEndDate(afUserSubscriptions.getEndDate());
            }
            if (item.getAddonId() != null) {
                AfUserAddons afUserAddon = subscriptionService.selectUserAddon(item.getClientUserAddonId());
                item.setStartDate(afUserAddon.getStartDate());
                item.setEndDate(afUserAddon.getEndDate());
            }
        }
        return list;
    }


    // /**
    // * 获取 jsapi_ticket（复用之前实现的逻辑，缓存优先）
    // */
    // private String getJsApiTicket() {
    // // 先查缓存
    //// String ticket = redisTemplate.opsForValue().get(REDIS_KEY_JSAPI_TICKET);
    //// if (ticket != null) {
    //// return ticket;
    //// }
    //
    // // 缓存失效，先获取 access_token（复用之前实现的 getAccessToken 方法）
    // String accessToken = getAccessToken();
    //
    // // 调用微信接口获取 jsapi_ticket
    // String ticketUrl = String.format(
    // "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",
    // accessToken
    // );
    // String response = new RestTemplate().getForObject(ticketUrl, String.class);
    // com.alibaba.fastjson.JSONObject result =
    // com.alibaba.fastjson.JSONObject.parseObject(response);
    //
    // if (result.getIntValue("errcode") != 0) {
    // throw new RuntimeException("获取 jsapi_ticket 失败：" +
    // result.getString("errmsg"));
    // }
    //
    // // 缓存 ticket
    // ticket = result.getString("ticket");
    // redisTemplate.opsForValue().set(REDIS_KEY_JSAPI_TICKET, ticket,
    // TICKET_EXPIRE_SEC, TimeUnit.SECONDS);
    //
    // return ticket;
    // }
}
