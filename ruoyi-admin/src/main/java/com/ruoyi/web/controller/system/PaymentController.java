package com.ruoyi.web.controller.system;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
//import com.ruoyi.system.service.PaymentService;
import com.ruoyi.system.service.SysPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private SysPaymentService paymentService;

    /**
     * 创建支付订单并生成二维码
     */
    @PostMapping("/create")
    public AjaxResult createPayment(
            @RequestParam BigDecimal amount,
            @RequestParam String paymentType) {
        try {
            PaymentQrCodeVO qrCodeVO = paymentService.createPayment(amount, paymentType);
            return AjaxResult.success(qrCodeVO);
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询支付状态
     */
    @GetMapping("/status")
    public AjaxResult checkPaymentStatus(@RequestParam String orderNo) {
        String status = paymentService.checkPaymentStatus(orderNo);
        return AjaxResult.success(status);
    }

    /**
     * 支付回调接口（供微信/支付宝调用）
     */
    @PostMapping("/callback/{paymentType}")
    public String paymentCallback(
            @PathVariable String paymentType,
            @RequestParam String orderNo,
            @RequestParam String sign) { // 实际需验证签名

        // 1. 验证签名（省略真实签名验证逻辑）
        boolean verifySuccess = true; // 模拟验签通过

        // 2. 处理回调
        if (verifySuccess) {
            boolean updateSuccess = paymentService.handlePaymentCallback(orderNo, paymentType);
            if (updateSuccess) {
                // 返回支付平台要求的成功标识（微信返回XML，支付宝返回success）
                return "wechat".equals(paymentType) ? 
                        "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>" : "success";
            }
        }
        return "fail";
    }
}