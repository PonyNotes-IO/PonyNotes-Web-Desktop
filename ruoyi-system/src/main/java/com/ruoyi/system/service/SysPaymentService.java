package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;

import java.math.BigDecimal;

public interface SysPaymentService extends IService<PaymentOrder> {
    /**
     * 创建支付订单并生成二维码
     */
    PaymentQrCodeVO createPayment(BigDecimal amount, String paymentType);

    /**
     * 查询支付状态
     */
    String checkPaymentStatus(String orderNo);

    /**
     * 处理支付回调（更新订单状态）
     */
    boolean handlePaymentCallback(String orderNo, String paymentType);
}