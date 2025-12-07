package com.ruoyi.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService {
    /**
     * 创建支付订单并生成二维码
     */
    PaymentResult createPayment(BigDecimal amount, String paymentType,String userInfo, String productName, String openid, String url, HttpServletRequest request);

    /**
     * 查询支付状态
     */
    String checkPaymentStatus(String orderNo);

    /**
     * 处理支付回调
     */
    boolean handlePaymentCallback(String orderNo, String paymentType);

    /**
     * 验证支付回调签名
     */
    boolean verifySign(String paymentType, String orderNo, Map<String, String> sign);

    Map<String, String> getWechatParams(HttpServletRequest request);

    /**
     * 获取支付订单信息
     */
    PaymentOrder getPaymentOrder(String outTradeNo);

    JSONObject getOpenid(String code);

    Map<String, String> getJsApiConfig(String url);
}