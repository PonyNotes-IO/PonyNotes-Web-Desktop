package com.ruoyi.system.service;

import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;

import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ISysPaymentService {

    void insert(PaymentOrder order);

    PaymentOrder selectOne(String orderNo);

    int updateById(PaymentOrder order);

    List<PaymentOrder> selectExpiredOrders(String pending, Date expireTime);

    PaymentOrder getPaymentOrder(String tradeNo);
}