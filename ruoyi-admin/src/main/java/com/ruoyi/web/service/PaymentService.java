package com.ruoyi.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.domain.vo.PaymentOrderVo;
import com.ruoyi.system.domain.vo.PaymentResult;
import com.ruoyi.system.domain.vo.PaymentListDTO;
import com.ruoyi.xmbj.domain.ClientUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    /**
     * 创建支付订单并生成二维码
     */
    PaymentResult createPayment(String paymentType, SysUser sysUser, ClientUser clientUser, String openid, String url, String planId, String billingType,String addonId, HttpServletRequest request);

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
    SysPaymentOrder getPaymentOrder(String outTradeNo);

    JSONObject getOpenid(String code);

    Map<String, String> getJsApiConfig(String url);

    /**
     * 获取用户下带分页的支付订单信息
     * @param paymentOrderVo
     * @return
     */
    List<PaymentOrderVo> userPaymentOrders(PaymentOrderVo paymentOrderVo);

    List<PaymentOrderVo> serPaymentOrders(PaymentOrderVo paymentOrderVo);

    List<PaymentListDTO> myPaymentList(String clientAuth);
}