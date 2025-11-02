package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
import com.ruoyi.system.mapper.SysPaymentOrderMapper;
import com.ruoyi.system.service.ISysPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysPaymentServiceImpl  implements ISysPaymentService {

    @Autowired
    private SysPaymentOrderMapper sysPaymentOrderMapper;


    @Override
    public void insert(PaymentOrder order) {
        sysPaymentOrderMapper.insert(order);
    }

    @Override
    public PaymentOrder selectOne(String orderNo) {
        return sysPaymentOrderMapper.selectOne(orderNo);
    }

    @Override
    public int updateById(PaymentOrder order) {
        return  sysPaymentOrderMapper.updateById(order);
    }

    @Override
    public List<PaymentOrder> selectExpiredOrders(String pending, Date expireTime) {
        return sysPaymentOrderMapper.selectExpiredOrders( expireTime);
    }
}