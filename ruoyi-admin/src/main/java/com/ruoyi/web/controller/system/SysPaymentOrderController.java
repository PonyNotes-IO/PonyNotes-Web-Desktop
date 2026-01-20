package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付订单管理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/paymentOrder")
public class SysPaymentOrderController extends BaseController
{
    private String prefix = "system/paymentOrder";

    @Autowired
    private ISysPaymentOrderService paymentOrderService;

    @PreAuthorize("hasPermi('system:paymentOrder:view')")
    @GetMapping()
    public String paymentOrder()
    {
        return prefix + "/paymentOrder";
    }

    /**
     * 查询支付订单列表
     */
    @PreAuthorize("hasPermi('system:paymentOrder:list')")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPaymentOrder paymentOrder)
    {
        startPage();
        List<SysPaymentOrder> list = paymentOrderService.selectPaymentOrderList(paymentOrder);
        return getDataTable(list);
    }

    /**
     * 导出支付订单列表
     */
    @Log(title = "支付订单", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasPermi('system:paymentOrder:export')")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPaymentOrder paymentOrder)
    {
        List<SysPaymentOrder> list = paymentOrderService.selectPaymentOrderList(paymentOrder);
        ExcelUtil<SysPaymentOrder> util = new ExcelUtil<SysPaymentOrder>(SysPaymentOrder.class);
        return util.exportExcel(list, "支付订单数据");
    }

    /**
     * 新增支付订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存支付订单
     */
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @PreAuthorize("hasPermi('system:paymentOrder:add')")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysPaymentOrder paymentOrder)
    {
        return toAjax(paymentOrderService.insertPaymentOrder(paymentOrder));
    }

    /**
     * 修改支付订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysPaymentOrder paymentOrder = paymentOrderService.selectPaymentOrderById(id);
        mmap.put("paymentOrder", paymentOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存支付订单
     */
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPermi('system:paymentOrder:edit')")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysPaymentOrder paymentOrder)
    {
        return toAjax(paymentOrderService.updatePaymentOrderById(paymentOrder));
    }

    /**
     * 删除支付订单
     */
    @Log(title = "支付订单", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPermi('system:paymentOrder:remove')")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(paymentOrderService.deletePaymentOrderByIds(ids));
    }

    /**
     * 查看支付订单详情
     */
    @PreAuthorize("hasPermi('system:paymentOrder:detail')")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysPaymentOrder paymentOrder = paymentOrderService.selectPaymentOrderById(id);
        mmap.put("paymentOrder", paymentOrder);
        return prefix + "/detail";
    }
}