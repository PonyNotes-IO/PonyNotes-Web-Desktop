package com.ruoyi.system.service;

import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.domain.vo.PaymentListDTO;
import com.ruoyi.system.domain.vo.PaymentOrderVo;

import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ISysPaymentService {

    void insert(SysPaymentOrder order);

    SysPaymentOrder selectOne(String orderNo);

    int updateById(SysPaymentOrder order);

    List<SysPaymentOrder> selectExpiredOrders(String pending, Date expireTime);

    SysPaymentOrder getPaymentOrder(String tradeNo);

    List<PaymentOrderVo> userPaymentOrders(PaymentOrderVo paymentOrderVo);

    List<PaymentListDTO> myPaymentList(String subject);
}