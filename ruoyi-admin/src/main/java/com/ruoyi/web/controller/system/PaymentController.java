package com.ruoyi.web.controller.system;


import com.alipay.api.internal.util.file.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
import com.ruoyi.web.service.PaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final Logger log = LoggerFactory.getLogger(PaymentController.class);
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
     * 支付宝回调接口
     */
    @PostMapping("/callback/alipay")
    public String alipayCallback(HttpServletRequest request) {
        try {
            // 1. 将request参数转换为Map
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            // 2. 验签
            boolean verifySuccess = paymentService.verifySign("alipay", null, params);
            if (!verifySuccess) {
                log.error("支付宝回调验签失败");
                return "fail";
            }

            // 3. 处理业务逻辑
            String outTradeNo = params.get("out_trade_no");
            String tradeStatus = params.get("trade_status");

            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                boolean success = paymentService.handlePaymentCallback(outTradeNo, "alipay");
                if (success) {
                    return "success"; // 支付宝要求返回 success 字符串
                }
            }
            
            return "fail";
        } catch (Exception e) {
            log.error("支付宝回调处理异常", e);
            return "fail";
        }
    }

    /**
     * 微信支付回调接口
     */
    @PostMapping("/callback/wechat")
    public String wechatCallback(HttpServletRequest request) {
        try {
            // 1. 读取请求body和header
            String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String signature = request.getHeader("Wechatpay-Signature");
            String serial = request.getHeader("Wechatpay-Serial");

            // 2. 验签
            Map<String, String> wechatParams = new HashMap<>();
            wechatParams.put("body", body);
            wechatParams.put("timestamp", timestamp);
            wechatParams.put("nonce", nonce);
            wechatParams.put("signature", signature);
            wechatParams.put("serial", serial);
            boolean verifySuccess = paymentService.verifySign("wechat", null, wechatParams);

            if (!verifySuccess) {
                log.error("微信支付回调验签失败");
                return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名验证失败]]></return_msg></xml>";
            }

            // 3. 解析通知数据
            JsonNode jsonNode = new ObjectMapper().readTree(body);
            if (!"SUCCESS".equals(jsonNode.get("trade_state").asText())) {
                return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[支付未成功]]></return_msg></xml>";
            }

            String outTradeNo = jsonNode.get("out_trade_no").asText();
            boolean success = paymentService.handlePaymentCallback(outTradeNo, "wechat");
            
            if (success) {
                return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            } else {
                return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[处理失败]]></return_msg></xml>";
            }
            
        } catch (Exception e) {
            log.error("微信支付回调处理异常", e);
            return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[系统异常]]></return_msg></xml>";
        }
    }

    // @PostMapping("/callback/alipay")
    // public String alipayNotify(HttpServletRequest request) {
    //     // 1) 将 request.getParameterMap() 转为 Map<String,String>
    //     Map<String,String> params = new HashMap<>();
    //     Map<String,String[]> requestParams = request.getParameterMap();
    //     for (String name : requestParams.keySet()) {
    //         String[] values = requestParams.get(name);
    //         String valueStr = String.join(",", values);
    //         params.put(name, valueStr);
    //     }
    //     // 2) 验签
    //     boolean ok = paymentService.verifySign("alipay",  null, params);
    //     if (ok) {
    //         String outTradeNo = params.get("out_trade_no");
    //         paymentService.handlePaymentCallback(outTradeNo, "alipay");
    //         return "success"; // 必须是支付宝指定的 plain text
    //     } else {
    //         return "fail";
    //     }
    // }
    // @PostMapping("/callback/wechat")
    // public String wechatNotify(HttpServletRequest request) throws IOException {
    //     String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
    //     String timestamp = request.getHeader("Wechatpay-Timestamp");
    //     String nonce = request.getHeader("Wechatpay-Nonce");
    //     String signature = request.getHeader("Wechatpay-Signature");
    //     String serial = request.getHeader("Wechatpay-Serial");

    //     // 传给 wechat verify 方法（需要你完善实现）
    //     Map<String, String> wechatNotifyParams = new HashMap<>();
    //     wechatNotifyParams.put("body", body);
    //     wechatNotifyParams.put("timestamp", timestamp);
    //     wechatNotifyParams.put("nonce", nonce);
    //     wechatNotifyParams.put("signature", signature);
    //     wechatNotifyParams.put("serial", serial);
    //     boolean ok = paymentService.verifySign("wechat", null, wechatNotifyParams);

    //     if (ok) {
    //         // 解析 body JSON 获取 out_trade_no
    //         String outTradeNo = parseOutTradeNoFromJson(body);
    //         if (outTradeNo == null || outTradeNo.isEmpty()) {
    //             log.error("无法从微信通知中解析出 out_trade_no, body={}", body);
    //             return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[MISSING out_trade_no]]></return_msg></xml>";
    //         }
    //         paymentService.handlePaymentCallback(outTradeNo, "wechat");
    //         return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    //     } else {
    //         return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[SIGNATURE FAIL]]></return_msg></xml>";
    //     }
    // }

    /**
     * 从微信通知的 JSON 文本中解析 out_trade_no 字段。
     */
    private String parseOutTradeNoFromJson(String body) {
        if (body == null || body.isEmpty()) {
            return null;
        }
        try {
            JsonNode root = new ObjectMapper().readTree(body);
            if (root == null) {
                return null;
            }
            // 直接根节点包含 out_trade_no
            if (root.has("out_trade_no") && !root.get("out_trade_no").isNull()) {
                return root.get("out_trade_no").asText();
            }
            // 部分微信回调把实际信息放在 resource 下
            if (root.has("resource") && root.get("resource").has("out_trade_no") && !root.get("resource").get("out_trade_no").isNull()) {
                return root.get("resource").get("out_trade_no").asText();
            }
            // 其他可能的嵌套或不同字段名可在此扩展
            return null;
        } catch (IOException e) {
            log.error("解析微信通知 JSON 异常", e);
            return null;
        }
    }

}