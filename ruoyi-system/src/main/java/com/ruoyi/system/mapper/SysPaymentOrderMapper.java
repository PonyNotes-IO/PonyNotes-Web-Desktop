package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPaymentOrder;

/**
 * 支付订单Mapper接口
 * 
 * @author 张继科
 * @date 2026-01-21
 */
public interface SysPaymentOrderMapper 
{
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
    public int deleteSysPaymentOrderByIds(Long[] ids);
}
