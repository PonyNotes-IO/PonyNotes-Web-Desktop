
package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.domain.vo.PaymentOrderVo;
import com.ruoyi.web.model.PaymentOrderDetailVo;
import com.ruoyi.web.service.PaymentService;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/ponynotes/order")
public class OrderController extends BaseController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * 订单列表（分页）
     */
    @PreAuthorize("@ss.hasPermi('auth:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(PaymentOrderVo paymentOrderVo) {
        if (paymentOrderVo.getUserId() == null) {
            return getDataTable(null);
        }
        startPage();
        List<PaymentOrderVo> list = paymentService.userPaymentOrders(paymentOrderVo);
        return getDataTable(list);
    }

    /**
     * 订单详情（包含关联的套餐/补充包信息）
     */
    @PreAuthorize("@ss.hasPermi('auth:order:query')")
    @GetMapping("/{orderNo}")
    public AjaxResult detail(@PathVariable String orderNo) {
        SysPaymentOrder order = paymentService.getPaymentOrder(orderNo);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        PaymentOrderDetailVo paymentOrderDetailVo = new PaymentOrderDetailVo();
        PaymentOrderVo orderVo = convertToVo(order);
        paymentOrderDetailVo.setPaymentOrderVo(orderVo);
        
        if (order.getPlanId() != null && !order.getPlanId().isEmpty()) {
            try {
                AfSubscriptionPlans plan = subscriptionService.getSubscriptionPlanById(Long.valueOf(order.getPlanId()));
                paymentOrderDetailVo.setAfSubscriptionPlans(plan);
            } catch (Exception e) {
                return AjaxResult.error("获取订阅计划失败");
            }
        }
        if (order.getAddonId() != null && !order.getAddonId().isEmpty()) {
            try {
                AfSubscriptionAddons addon = subscriptionService.getAddonById(Long.valueOf(order.getAddonId()));
                paymentOrderDetailVo.setAfSubscriptionAddonsList(Collections.singletonList(addon));
            } catch (Exception e) {
                return AjaxResult.error("获取补充包失败");
            }
        }
        return AjaxResult.success(paymentOrderDetailVo);
    }

    /**
     * 获取订单的订阅详情（兼容旧接口）
     */
    @PreAuthorize("@ss.hasPermi('auth:order:list')")
    @GetMapping("/subscription")
    public AjaxResult getSubscription(PaymentOrderVo paymentOrderVo) {
        if (paymentOrderVo.getUserId() == null) {
            return AjaxResult.error("用户为空");
        }
        if (paymentOrderVo.getPlanId() == null && paymentOrderVo.getAddonId() == null) {
            return AjaxResult.error("绑定服务为空");
        }
        PaymentOrderDetailVo paymentOrderDetailVo = new PaymentOrderDetailVo();
        paymentOrderDetailVo.setPaymentOrderVo(paymentOrderVo);
        if (paymentOrderVo.getPlanId() != null) {
            try {
                AfSubscriptionPlans plan = subscriptionService.getSubscriptionPlanById(Long.valueOf(paymentOrderVo.getPlanId()));
                paymentOrderDetailVo.setAfSubscriptionPlans(plan);
            } catch (Exception e) {
                return AjaxResult.error("获取订阅计划失败");
            }
        }
        if (paymentOrderVo.getAddonId() != null) {
            try {
                AfSubscriptionAddons addon = subscriptionService.getAddonById(Long.valueOf(paymentOrderVo.getAddonId()));
                paymentOrderDetailVo.setAfSubscriptionAddonsList(Collections.singletonList(addon));
            } catch (Exception e) {
                return AjaxResult.error("获取补充包失败");
            }
        }
        return AjaxResult.success(paymentOrderDetailVo);
    }

    private PaymentOrderVo convertToVo(SysPaymentOrder order) {
        PaymentOrderVo vo = new PaymentOrderVo();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setAmount(order.getAmount());
        vo.setPaymentType(order.getPaymentType());
        vo.setQrCodeUrl(order.getQrCodeUrl());
        vo.setStatus(order.getStatus());
        vo.setCreateTime(order.getCreateTime());
        vo.setUpdateTime(order.getUpdateTime());
        vo.setPayTime(order.getPayTime());
        vo.setUserId(order.getUserId());
        vo.setUserInfo(order.getUserInfo());
        vo.setProductName(order.getProductName());
        vo.setClientUserId(order.getClientUserId());
        vo.setClientSubscriptionId(order.getClientSubscriptionId());
        vo.setClientUserAddonId(order.getClientUserAddonId());
        vo.setPlanId(order.getPlanId());
        vo.setAddonId(order.getAddonId());
        vo.setBillingType(order.getBillingType());
        vo.setQuantity(order.getQuantity());
        vo.setAfstatus(order.getStatus());
        // startDate and endDate are not in PaymentOrder, leave null
        return vo;
    }
}
