package com.ruoyi.system.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.domain.PaymentOrder;

import io.lettuce.core.dynamic.annotation.Param;

public interface SysPaymentOrderMapper {

    void insert(PaymentOrder order);

    PaymentOrder selectOne(String orderNo);

    int  updateById(PaymentOrder order);

    List<PaymentOrder> selectExpiredOrders(Date expireTime);
}
