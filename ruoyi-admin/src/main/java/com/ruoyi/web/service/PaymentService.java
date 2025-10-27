package com.ruoyi.web.service;

import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService {
    /**
     * 创建支付订单并生成二维码
     */
    PaymentQrCodeVO createPayment(BigDecimal amount, String paymentType);

    /**
     * 查询支付状态
     */
    String checkPaymentStatus(String orderNo);

    /**
     * 处理支付回调
     */
    boolean handlePaymentCallback(String orderNo, String paymentType);

    boolean verifySign(String paymentType, String orderNo, Map<String, String> sign);
}