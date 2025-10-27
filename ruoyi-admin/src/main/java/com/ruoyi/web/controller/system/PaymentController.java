package com.ruoyi.web.controller.system;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
import com.ruoyi.web.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

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
    public AjaxResult paymentCallback(
            @PathVariable String paymentType,
            @RequestParam String orderNo,
            @RequestParam Map<String, String> sign) { // 实际需验证签名

        // 1. verify
        boolean verifySuccess = false;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> signMap = objectMapper.convertValue(sign,Map.class);
        verifySuccess = paymentService.verifySign(paymentType,orderNo,signMap);

        // 2. 处理回调
        if (verifySuccess) {
            boolean updateSuccess = paymentService.handlePaymentCallback(orderNo, paymentType);
            return AjaxResult.success("支付成功");
            //            if (updateSuccess) {
//                // 返回支付平台要求的成功标识（微信返回XML，支付宝返回success）
////                return "wechat".equals(paymentType) ?
////                        "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>" : "success";
//            }
        }
        return AjaxResult.error("支付失败");
    }



}