package com.ruoyi.system.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.domain.vo.PaymentListDTO;
import com.ruoyi.system.domain.vo.PaymentOrderVo;

/**
 * 支付订单Mapper接口
 * 
 * @author 张继科
 * @date 2026-01-21
 */
public interface SysPaymentOrderMapper
{
    /**
     * 新增支付订单
     * 
     * @param paymentOrder 支付订单对象
     * @return 结果
     */
    public int insert(SysPaymentOrder paymentOrder);

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
     * @param tradeNo 交易单号,orderNo
     * @return 支付订单对象
     */
    public SysPaymentOrder getPaymentOrder(String tradeNo);

    /**
     * 查询支付订单列表
     * 
     * @param paymentOrder 支付订单对象
     * @return 支付订单列表
     */
    public List<SysPaymentOrder> selectPaymentOrderList(SysPaymentOrder paymentOrder);
    /**
     * 查询支付订单
     * 
     * @param id 支付订单主键
     * @return 支付订单
     */
    public SysPaymentOrder selectSysPaymentOrderById(Long id);

    /**
     * 查询支付订单列表
     * 
     * @param sysPaymentOrder 支付订单
     * @return 支付订单集合
     */
    public List<SysPaymentOrder> selectSysPaymentOrderList(SysPaymentOrder sysPaymentOrder);

    /**
     * 新增支付订单
     * 
     * @param sysPaymentOrder 支付订单
     * @return 结果
     */
    public int insertSysPaymentOrder(SysPaymentOrder sysPaymentOrder);

    /**
     * 修改支付订单
     * 
     * @param sysPaymentOrder 支付订单
     * @return 结果
     */
    public int updateSysPaymentOrder(SysPaymentOrder sysPaymentOrder);
    /**
     * 根据ID查询支付订单
     * 
     * @param id 订单ID
     * @return 支付订单对象
     */
    public SysPaymentOrder selectPaymentOrderById(Long id);
    /**
     * 删除支付订单
     * 
     * @param id 支付订单主键
     * @return 结果
     */
    public int deleteSysPaymentOrderById(Long id);
    /**
     * 批量删除支付订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePaymentOrderByIds(Long[] ids);

    List<PaymentOrderVo> userPaymentOrders(PaymentOrderVo paymentOrderVo);

    int deleteSysPaymentOrderByIds(Long[] ids);

    List<PaymentListDTO> myPaymentList(String userInfo);
}