package com.ruoyi.web.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.file.IOUtils;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysPaymentService;
import com.ruoyi.web.config.AlipayConfig;
//import com.ruoyi.web.config.WechatPayConfig;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
import com.ruoyi.web.service.PaymentService;
import com.ruoyi.web.util.OrderNoGenerator;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;

import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import com.wechat.pay.java.service.payments.nativepay.model.QueryOrderByOutTradeNoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
//    @Autowired
//    private NativePayService wechatNativePayService;  // 微信Native支付服务
//    @Autowired
//    private WechatPayConfig wechatPayConfig;          // 微信支付配置
    @Autowired
    private AlipayClient alipayClient;                // 支付宝客户端
    @Autowired
    private AlipayConfig alipayConfig;                // 支付宝配置

    @Autowired
    private ISysPaymentService paymentService;  //项目系统订单服务

    /**
     * 创建支付订单并生成二维码（微信/支付宝真实调用）
     */
    @Override
    public PaymentQrCodeVO createPayment(BigDecimal amount, String paymentType) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("支付金额非法：{}", amount);
            throw new IllegalArgumentException("支付金额必须大于0");
        }
        // 1. 生成唯一订单号orderNoGenerator
//        String orderNo = generateOrderNo(paymentType);
        String orderNo = OrderNoGenerator.generate(paymentType);
        String qrCodeUrl = "";

        try {
            // 2. 根据支付方式调用对应SDK生成二维码
            if ("wechat".equals(paymentType)) {
                qrCodeUrl ="";
//                qrCodeUrl = createWechatQrCode(orderNo, amount);

            } else if ("alipay".equals(paymentType)) {
                qrCodeUrl = createAlipayQrCode(orderNo, amount);
            } else {
                throw new IllegalArgumentException("不支持的支付方式：" + paymentType);
            }

            // 3. 保存订单
            PaymentOrder order = new PaymentOrder();
            order.setOrderNo(orderNo);
            order.setAmount(amount);
            order.setPaymentType(paymentType);
            order.setQrCodeUrl(qrCodeUrl);
            order.setStatus("pending"); // 待支付
            order.setCreateTime(new Date());
            paymentService.insert(order);

            // 4. 返回二维码信息
            PaymentQrCodeVO vo = new PaymentQrCodeVO();
            vo.setOrderNo(orderNo);
            vo.setQrCodeUrl(qrCodeUrl);
            vo.setExpireTime(DateUtils.addMinutes(new Date(), 15)); // 15分钟过期
            return vo;

        } catch (Exception e) {
            log.error("生成支付二维码失败", e);
            throw new RuntimeException("支付订单创建失败：" + e.getMessage());
        }
    }

    /**
     * 微信支付：生成Native支付二维码
     */
//    private String createWechatQrCode(String orderNo, BigDecimal amount) throws Exception {
//        // 构建请求参数
//        PrepayRequest request = new PrepayRequest();
//        request.setOutTradeNo(orderNo);  // 商户订单号
//        request.setAppid(wechatPayConfig.getAppId());  // 公众号/小程序APPID
//        request.setMchid(wechatPayConfig.getMchId());  // 商户号
//        request.setDescription("会员充值-" + orderNo);  // 订单描述
//        request.setNotifyUrl(wechatPayConfig.getNotifyUrl());  // 回调地址
//
//        // 金额（单位：分）
//        Amount amountObj = new Amount();
//        amountObj.setTotal(amount.multiply(new BigDecimal(100)).intValue()); // 元转分
//        request.setAmount(amountObj);
//
//        // 调用微信支付SDK生成二维码
////        PrepayResponse response = wechatNativePayService.prepay(request);
////        return response.getCodeUrl();  // 返回微信支付二维码链接
//        return "";
//    }

    /**
     * 支付宝：生成预下单二维码
     */
    private String createAlipayQrCode(String orderNo, BigDecimal amount) throws Exception {
        // 构建请求参数
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl());  // 回调地址

        // 业务参数（JSON格式）
        String bizContent = "{" +
                "\"out_trade_no\":\"" + orderNo + "\"," +
                "\"total_amount\":\"" + amount.setScale(2) + "\"," +  // 金额（元，保留2位小数）
                "\"subject\":\"会员充值-" + orderNo + "\"," +  // 订单标题
                "\"timeout_express\":\"15m\"" +  // 过期时间15分钟
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
//        // 调用支付宝SDK生成二维码
//        AlipayTradePrecreateResponse response = alipayClient.execute(request);
//        if (!response.isSuccess()) {
//            throw new RuntimeException("支付宝二维码生成失败：" + response.getMsg());
//        }
//        return response.getQrCode();  // 返回支付宝二维码链接
        throw new RuntimeException("支付宝二维码生成失败，已达到最大重试次数");
    }

    /**
     * 查询支付状态（真实调用支付平台接口）
     */

    @Override
    public String checkPaymentStatus(String orderNo) {
        // 1. 查询本地订单
        PaymentOrder order = paymentService.selectOne(orderNo);
        if (order == null) {
            return "invalid"; // 订单不存在
        }

        // 2. 已支付状态直接返回
        if ("success".equals(order.getStatus())) {
            return "success";
        }

        // 3. 调用支付平台接口查询最新状态
        try {
             if ("alipay".equals(order.getPaymentType())) {
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
//    @Override
//    public String checkPaymentStatus(String orderNo) {
//        // 1. 查询本地订单
//        PaymentOrder order = paymentService.selectOne(orderNo);
//        if (order == null) {
//            return "invalid"; // 订单不存在
//        }
//
//        // 2. 已支付状态直接返回
//        if ("success".equals(order.getStatus())) {
//            return "success";
//        }
//
//        // 3. 调用支付平台接口查询最新状态
//        try {
//            if ("wechat".equals(order.getPaymentType())) {
//                // 微信支付查询（通过商户订单号）
//                QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
//                request.setOutTradeNo(orderNo);
//                Transaction  response = wechatNativePayService.queryOrderByOutTradeNo(request);
//                // 微信支付状态：SUCCESS=支付成功，REFUND=已退款，NOTPAY=未支付，CLOSED=已关闭，REVOKED=已撤销（付款码支付）
//                if ("NOTPAY".equals(response.getTradeState())) {
//                    // 检查是否已过期
//                    if (order.getCreateTime().before(DateUtils.addMinutes(new Date(), -15))) {
//                        order.setStatus("expired");
//                        paymentService.updateById(order);
//                        return "expired";
//                    }
//                    return "pending"; // 仍在有效期内，未支付
//                }else if ("SUCCESS".equals(response.getTradeState())) {
//                    // 更新本地状态为成功
//                    order.setStatus("success");
//                    order.setPayTime(new Date());
//                    paymentService.updateById(order);
//                    return "success";
//                } else if ("CLOSED".equals(response.getTradeState()) || "REVOKED".equals(response.getTradeState())) {
//                    order.setStatus("failed");
//                    paymentService.updateById(order);
//                    return "failed";
//                }
//            } else if ("alipay".equals(order.getPaymentType())) {
//                // 支付宝支付查询
//                AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//                request.setBizContent("{" +
//                        "\"out_trade_no\":\"" + orderNo + "\"" +
//                        "}");
//                AlipayTradeQueryResponse response = alipayClient.execute(request);
//                if (response.isSuccess()) {
//                    // 支付宝交易状态：TRADE_SUCCESS=支付成功，TRADE_CLOSED=交易关闭
//                    if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
//                        order.setStatus("success");
//                        order.setPayTime(new Date());
//                        paymentService.updateById(order);
//                        return "success";
//                    } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {
//                        order.setStatus("failed");
//                        paymentService.updateById(order);
//                        return "failed";
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("查询支付状态失败，订单号：{}", orderNo, e);
//        }
//
//        // 4. 未支付或查询失败，返回当前本地状态
//        return order.getStatus();
//    }

    /**
     * 处理支付回调（更新订单状态）
     */
    @Override
    public boolean handlePaymentCallback(String orderNo, String paymentType) {
        PaymentOrder order = paymentService.selectOne(orderNo);
        if (order == null || !"pending".equals(order.getStatus())) {
            return false;
        }
        boolean result = false;
        // 更新订单状态为已支付
        order.setStatus("success");
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());
        try {
            paymentService.updateById(order);
            result =true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return  result;
        }
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
                verifySuccess =true;
//                verifySuccess = wechatPayConfig.verifySign(wechatParams);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("支付回调验签失败", e);
            return false;
        }finally {
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
//        wechatParams.put("apiV3Key", wechatPayConfig.getApiV3Key()); // 传入APIv3密钥
        return wechatParams;
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo(String paymentType) {
        return paymentType + "_" + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().substring(0, 8);
    }
}
