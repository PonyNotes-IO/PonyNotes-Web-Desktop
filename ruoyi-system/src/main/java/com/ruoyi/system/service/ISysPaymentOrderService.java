package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysPaymentOrder;
import java.util.Date;
import java.util.List;

/**
 * 支付订单 服务层
 * 
 * @author ruoyi
 */
public interface ISysPaymentOrderService
{
    /**
     * 新增支付订单
     * 
     * @param paymentOrder 支付订单对象
     * @return 结果
     */
    public int insertPaymentOrder(SysPaymentOrder paymentOrder);

    /**
     * 根据订单号查询支付订单
     * 
     * @param orderNo 订单编号
     * @return 支付订单对象
     */
    public SysPaymentOrder selectPaymentOrderByOrderNo(String orderNo);

    /**
     * 根据ID更新支付订单
     * 
     * @param paymentOrder 支付订单对象
     * @return 结果
     */
    public int updatePaymentOrderById(SysPaymentOrder paymentOrder);

    /**
     * 查询过期订单
     * 
     * @param payTime 支付时间
     * @return 过期订单列表
     */
    public List<SysPaymentOrder> selectExpiredOrders(Date payTime);

    /**
     * 根据订单号查询支付订单
     * 
     * @param tradeNo 交易单号
     * @return 支付订单对象
     */
    public SysPaymentOrder getPaymentOrderByTradeNo(String tradeNo);

    /**
     * 查询支付订单列表
     * 
     * @param paymentOrder 支付订单对象
     * @return 支付订单列表
     */
    public List<SysPaymentOrder> selectPaymentOrderList(SysPaymentOrder paymentOrder);

    /**
     * 根据ID查询支付订单
     * 
     * @param id 订单ID
     * @return 支付订单对象
     */
    public SysPaymentOrder selectPaymentOrderById(Long id);

    /**
     * 批量删除支付订单
     * 
     * @param ids 需要删除的订单ID字符串
     * @return 结果
     */
    public int deletePaymentOrderByIds(String ids);

    /**
     * 删除支付订单
     * 
     * @param id 订单ID
     * @return 结果
     */
    public int deletePaymentOrderById(Long id);
}