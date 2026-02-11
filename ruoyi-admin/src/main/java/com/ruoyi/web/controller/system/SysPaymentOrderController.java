package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentOrderService;

/**
 * 支付订单表 控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/payment")
public class SysPaymentOrderController extends BaseController
{
    @Autowired
    private ISysPaymentOrderService sysPaymentOrderService;

    /**
     * 查询支付订单表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysPaymentOrder sysPaymentOrder)
    {
        startPage();
        List<SysPaymentOrder> list = sysPaymentOrderService.selectSysPaymentOrderList(sysPaymentOrder);
        return getDataTable(list);
    }

    /**
     * 获取支付订单表详细信息
     */
    @GetMapping("/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysPaymentOrderService.selectSysPaymentOrderById(id));
    }

    /**
     * 新增支付订单表
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysPaymentOrder sysPaymentOrder)
    {
        return toAjax(sysPaymentOrderService.insertSysPaymentOrder(sysPaymentOrder));
    }

    /**
     * 修改支付订单表
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SysPaymentOrder sysPaymentOrder)
    {
        return toAjax(sysPaymentOrderService.updateSysPaymentOrder(sysPaymentOrder));
    }

    /**
     * 删除支付订单表
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysPaymentOrderService.deleteSysPaymentOrderByIds(ids));
    }

    /**
     * 根据订单编号查询支付订单
     */
    @GetMapping("/byOrderNo/{orderNo}")
    public AjaxResult getByOrderNo(@PathVariable("orderNo") String orderNo)
    {
        return AjaxResult.success(sysPaymentOrderService.selectPaymentOrderByOrderNo(orderNo));
    }

    /**
     * 更新订单状态为已支付
     */
    @PutMapping("/success")
    public AjaxResult updateStatusToSuccess(@RequestBody SysPaymentOrder order)
    {
        int result = sysPaymentOrderService.updateOrderStatusToSuccess(
            order.getOrderNo(), order.getPayTime(), order.getMemberExpireTime());
        return toAjax(result);
    }
}
