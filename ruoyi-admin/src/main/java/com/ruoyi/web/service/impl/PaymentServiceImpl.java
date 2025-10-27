//package com.ruoyi.web.service.impl;
//
//import com.alipay.api.AlipayClient;
//import com.alipay.api.request.AlipayTradePreCreateRequest;
//import com.alipay.api.response.AlipayTradePreCreateResponse;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.ruoyi.common.utils.DateUtils;
//import com.ruoyi.web.config.AlipayConfig;
//import com.ruoyi.web.config.WechatPayConfig;
//import com.ruoyi.system.domain.PaymentOrder;
//import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
//import com.ruoyi.system.mapper.PaymentOrderMapper;
////import com.ruoyi.system.service.PaymentService;
//import com.wechat.pay.java.service.payments.nativepay.NativePayService;
//import com.wechat.pay.java.service.payments.nativepay.model.Amount;
//import com.wechat.pay.java.service.payments.nativepay.model.CreateOrderRequest;
//import com.wechat.pay.java.service.payments.nativepay.model.CreateOrderResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.UUID;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    @Autowired
//    private NativePayService wechatNativePayService;  // 微信Native支付服务
//    @Autowired
//    private WechatPayConfig wechatPayConfig;          // 微信支付配置
//    @Autowired
//    private AlipayClient alipayClient;                // 支付宝客户端
//    @Autowired
//    private AlipayConfig alipayConfig;                // 支付宝配置
//
//    /**
//     * 创建支付订单并生成二维码（微信/支付宝真实调用）
//     */
//    @Override
//    public PaymentQrCodeVO createPayment(BigDecimal amount, String paymentType) {
//        // 1. 生成唯一订单号
//        String orderNo = generateOrderNo(paymentType);
//        String qrCodeUrl = "";
//
//        try {
//            // 2. 根据支付方式调用对应SDK生成二维码
//            if ("wechat".equals(paymentType)) {
//                qrCodeUrl = createWechatQrCode(orderNo, amount);
//            } else if ("alipay".equals(paymentType)) {
//                qrCodeUrl = createAlipayQrCode(orderNo, amount);
//            } else {
//                throw new IllegalArgumentException("不支持的支付方式：" + paymentType);
//            }
//
//            // 3. 保存订单
//            PaymentOrder order = new PaymentOrder();
//            order.setOrderNo(orderNo);
//            order.setAmount(amount);
//            order.setPaymentType(paymentType);
//            order.setQrCodeUrl(qrCodeUrl);
//            order.setStatus("pending"); // 待支付
//            order.setCreateTime(new Date());
//            baseMapper.insert(order);
//
//            // 4. 返回二维码信息
//            PaymentQrCodeVO vo = new PaymentQrCodeVO();
//            vo.setOrderNo(orderNo);
//            vo.setQrCodeUrl(qrCodeUrl);
//            vo.setExpireTime(DateUtils.addMinutes(new Date(), 15)); // 15分钟过期
//            return vo;
//
//        } catch (Exception e) {
//            log.error("生成支付二维码失败", e);
//            throw new RuntimeException("支付订单创建失败：" + e.getMessage());
//        }
//    }
//
//    /**
//     * 微信支付：生成Native支付二维码
//     */
//    private String createWechatQrCode(String orderNo, BigDecimal amount) throws Exception {
//        // 构建请求参数
//        CreateOrderRequest request = new CreateOrderRequest();
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
//        CreateOrderResponse response = wechatNativePayService.createOrder(request);
//        return response.getCodeUrl();  // 返回微信支付二维码链接
//    }
//
//    /**
//     * 支付宝：生成预下单二维码
//     */
//    private String createAlipayQrCode(String orderNo, BigDecimal amount) throws Exception {
//        // 构建请求参数
//        AlipayTradePreCreateRequest request = new AlipayTradePreCreateRequest();
//        request.setNotifyUrl(alipayConfig.getNotifyUrl());  // 回调地址
//
//        // 业务参数（JSON格式）
//        String bizContent = "{" +
//                "\"out_trade_no\":\"" + orderNo + "\"," +
//                "\"total_amount\":\"" + amount.setScale(2) + "\"," +  // 金额（元，保留2位小数）
//                "\"subject\":\"会员充值-" + orderNo + "\"," +  // 订单标题
//                "\"timeout_express\":\"15m\"" +  // 过期时间15分钟
//                "}";
//        request.setBizContent(bizContent);
//
//        // 调用支付宝SDK生成二维码
//        AlipayTradePreCreateResponse response = alipayClient.execute(request);
//        if (!response.isSuccess()) {
//            throw new RuntimeException("支付宝二维码生成失败：" + response.getMsg());
//        }
//        return response.getQrCode();  // 返回支付宝二维码链接
//    }
//
//    /**
//     * 查询支付状态（真实调用支付平台接口）
//     */
//    @Override
//    public String checkPaymentStatus(String orderNo) {
//        // 1. 查询本地订单
//        PaymentOrder order = baseMapper.selectOne(
//                new QueryWrapper<PaymentOrder>().eq("order_no", orderNo)
//        );
//        if (order == null) {
//            return "invalid"; // 订单不存在
//        }
//
//        // 2. 已支付状态直接返回
//        if ("success".equals(order.getStatus())) {
//            return "success";
//        }
//
//        // 3. 调用支付平台接口查询最新状态（此处简化，实际需调用微信/支付宝的查询接口）
//        // 微信：调用NativePayService.queryOrder
//        // 支付宝：调用AlipayTradeQueryRequest
//        return order.getStatus(); // 暂时返回本地状态，实际需对接查询接口
//    }
//
//    /**
//     * 处理支付回调（更新订单状态）
//     */
//    @Override
//    public boolean handlePaymentCallback(String orderNo, String paymentType) {
//        PaymentOrder order = baseMapper.selectOne(
//                new QueryWrapper<PaymentOrder>().eq("order_no", orderNo)
//        );
//        if (order == null || !"pending".equals(order.getStatus())) {
//            return false;
//        }
//
//        // 更新订单状态为已支付
//        order.setStatus("success");
//        order.setPayTime(new Date());
//        order.setUpdateTime(new Date());
//        return baseMapper.updateById(order) > 0;
//    }
//
//    /**
//     * 生成订单号
//     */
//    private String generateOrderNo(String paymentType) {
//        return paymentType + "_" + System.currentTimeMillis() + "_" +
//                UUID.randomUUID().toString().substring(0, 8);
//    }
//}
