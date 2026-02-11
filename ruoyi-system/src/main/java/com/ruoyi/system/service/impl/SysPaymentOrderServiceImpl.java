package com.ruoyi.system.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysPaymentOrderMapper;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentOrderService;

/**
 * 支付订单Service业务层处理
 * 
 * @author 张继科
 * @date 2026-01-21
 */
@Service
public class SysPaymentOrderServiceImpl implements ISysPaymentOrderService 
{
    @Autowired
    private SysPaymentOrderMapper sysPaymentOrderMapper;

    /**
     * 查询支付订单
     * 
     * @param id 支付订单主键
     * @return 支付订单
     */
    @Override
    public SysPaymentOrder selectSysPaymentOrderById(Long id)
    {
        return sysPaymentOrderMapper.selectSysPaymentOrderById(id);
    }

    @Override
    public SysPaymentOrder selectPaymentOrderByOrderNo(String orderNo) {
        return null;
    }

    /**
     * 查询支付订单列表
     * 
     * @param sysPaymentOrder 支付订单
     * @return 支付订单
     */
    @Override
    public List<SysPaymentOrder> selectSysPaymentOrderList(SysPaymentOrder sysPaymentOrder)
    {
        return sysPaymentOrderMapper.selectSysPaymentOrderList(sysPaymentOrder);
    }

    /**
     * 新增支付订单
     * 
     * @param sysPaymentOrder 支付订单
     * @return 结果
     */
    @Override
    public int insertSysPaymentOrder(SysPaymentOrder sysPaymentOrder)
    {
        sysPaymentOrder.setCreateTime(DateUtils.getNowDate());
        return sysPaymentOrderMapper.insertSysPaymentOrder(sysPaymentOrder);
    }

    /**
     * 修改支付订单
     * 
     * @param sysPaymentOrder 支付订单
     * @return 结果
     */
    @Override
    public int updateSysPaymentOrder(SysPaymentOrder sysPaymentOrder)
    {
        sysPaymentOrder.setUpdateTime(DateUtils.getNowDate());
        return sysPaymentOrderMapper.updateSysPaymentOrder(sysPaymentOrder);
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

    /**
     * 批量删除支付订单
     * 
     * @param ids 需要删除的支付订单主键
     * @return 结果
     */
    @Override
    public int deleteSysPaymentOrderByIds(Long[] ids)
    {
        return sysPaymentOrderMapper.deleteSysPaymentOrderByIds(ids);
    }

    /**
     * 删除支付订单信息
     * 
     * @param id 支付订单主键
     * @return 结果
     */
    @Override
    public int deleteSysPaymentOrderById(Long id)
    {
        return sysPaymentOrderMapper.deleteSysPaymentOrderById(id);
    }

    /**
     * 更新订单状态为已支付
     * 
     * @param orderNo 订单编号
     * @param payTime 支付时间
     * @param memberExpireTime 会员到期时间
     * @return 结果
     */
    @Override
    public int updateOrderStatusToSuccess(String orderNo, Date payTime, Date memberExpireTime) {
        if(StringUtils.isEmpty(orderNo)) {
            throw new RuntimeException("参数订单号必须不为空");
        }
        SysPaymentOrder order = new SysPaymentOrder();
        order.setOrderNo(orderNo);
        order.setStatus("success");
        order.setPayTime(payTime);
        order.setMemberExpireTime(memberExpireTime);
        order.setUpdateTime(new java.util.Date());
        return sysPaymentOrderMapper.updateSysPaymentOrder(order);
    }
}
