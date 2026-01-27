package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentOrderService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ISysPaymentOrderServiceImpl implements ISysPaymentOrderService {
    @Override
    public int insertPaymentOrder(SysPaymentOrder paymentOrder) {
        return 0;
    }

    @Override
    public SysPaymentOrder selectPaymentOrderByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public int updatePaymentOrderById(SysPaymentOrder paymentOrder) {
        return 0;
    }

    @Override
    public List<SysPaymentOrder> selectExpiredOrders(Date payTime) {
        return Collections.emptyList();
    }

    @Override
    public SysPaymentOrder getPaymentOrderByTradeNo(String tradeNo) {
        return null;
    }

    @Override
    public List<SysPaymentOrder> selectPaymentOrderList(SysPaymentOrder paymentOrder) {
        return Collections.emptyList();
    }

    @Override
    public SysPaymentOrder selectPaymentOrderById(Long id) {
        return null;
    }

    @Override
    public int deletePaymentOrderByIds(String ids) {
        return 0;
    }

    @Override
    public int deletePaymentOrderById(Long id) {
        return 0;
    }
}
