package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.mapper.SysPaymentOrderMapper;
import com.ruoyi.system.service.ISysPaymentOrderService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 支付订单 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysPaymentOrderServiceImpl implements ISysPaymentOrderService
{
    @Autowired
    private SysPaymentOrderMapper paymentOrderMapper;

    /**
     * 新增支付订单
     * 
     * @param paymentOrder 支付订单对象
     * @return 结果
     */
    @Override
    public int insertPaymentOrder(SysPaymentOrder paymentOrder)
    {
        return paymentOrderMapper.insert(paymentOrder);
    }

    /**
     * 根据订单号查询支付订单
     * 
     * @param orderNo 订单编号
     * @return 支付订单对象
     */
    @Override
    public SysPaymentOrder selectPaymentOrderByOrderNo(String orderNo)
    {
        return paymentOrderMapper.selectOne(orderNo);
    }

    /**
     * 根据ID更新支付订单
     * 
     * @param paymentOrder 支付订单对象
     * @return 结果
     */
    @Override
    public int updatePaymentOrderById(SysPaymentOrder paymentOrder)
    {
        return paymentOrderMapper.updateById(paymentOrder);
    }

    /**
     * 查询过期订单
     * 
     * @param payTime 支付时间
     * @return 过期订单列表
     */
    @Override
    public List<SysPaymentOrder> selectExpiredOrders(Date payTime)
    {
        return paymentOrderMapper.selectExpiredOrders(payTime);
    }

    /**
     * 根据订单号查询支付订单
     * 
     * @param tradeNo 交易单号
     * @return 支付订单对象
     */
    @Override
    public SysPaymentOrder getPaymentOrderByTradeNo(String tradeNo)
    {
        return paymentOrderMapper.getPaymentOrder(tradeNo);
    }

    /**
     * 查询支付订单列表
     * 
     * @param paymentOrder 支付订单对象
     * @return 支付订单列表
     */
    @Override
    public List<SysPaymentOrder> selectPaymentOrderList(SysPaymentOrder paymentOrder)
    {
        return paymentOrderMapper.selectPaymentOrderList(paymentOrder);
    }

    /**
     * 根据ID查询支付订单
     * 
     * @param id 订单ID
     * @return 支付订单对象
     */
    @Override
    public SysPaymentOrder selectPaymentOrderById(Long id)
    {
        return paymentOrderMapper.selectPaymentOrderById(id);
    }

    /**
     * 批量删除支付订单
     * 
     * @param ids 需要删除的订单ID字符串
     * @return 结果
     */
    @Override
    public int deletePaymentOrderByIds(String ids)
    {
        return paymentOrderMapper.deletePaymentOrderByIds(Convert.toLongArray(ids));
    }

    /**
     * 删除支付订单
     * 
     * @param id 订单ID
     * @return 结果
     */
    @Override
    public int deletePaymentOrderById(Long id)
    {
        return paymentOrderMapper.deletePaymentOrderByIds(new Long[]{id});
    }
}