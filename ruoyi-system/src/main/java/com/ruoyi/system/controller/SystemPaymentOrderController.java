package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.service.ISysPaymentOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 支付订单Controller
 * 
 * @author 张继科
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/system/order")
public class SystemPaymentOrderController extends BaseController
{
    @Autowired
    private ISysPaymentOrderService sysPaymentOrderService;

    /**
     * 查询支付订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPaymentOrder sysPaymentOrder)
    {
        startPage();
        List<SysPaymentOrder> list = sysPaymentOrderService.selectSysPaymentOrderList(sysPaymentOrder);
        return getDataTable(list);
    }

    /**
     * 导出支付订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "支付订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPaymentOrder sysPaymentOrder)
    {
        List<SysPaymentOrder> list = sysPaymentOrderService.selectSysPaymentOrderList(sysPaymentOrder);
        ExcelUtil<SysPaymentOrder> util = new ExcelUtil<SysPaymentOrder>(SysPaymentOrder.class);
        util.exportExcel(response, list, "支付订单数据");
    }

    /**
     * 获取支付订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysPaymentOrderService.selectSysPaymentOrderById(id));
    }

    /**
     * 新增支付订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPaymentOrder sysPaymentOrder)
    {
        return toAjax(sysPaymentOrderService.insertSysPaymentOrder(sysPaymentOrder));
    }

    /**
     * 修改支付订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPaymentOrder sysPaymentOrder)
    {
        return toAjax(sysPaymentOrderService.updateSysPaymentOrder(sysPaymentOrder));
    }

    /**
     * 删除支付订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "支付订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysPaymentOrderService.deleteSysPaymentOrderByIds(ids));
    }
}
