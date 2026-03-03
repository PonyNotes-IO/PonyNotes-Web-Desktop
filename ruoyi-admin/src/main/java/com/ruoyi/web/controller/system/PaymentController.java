package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.file.IOUtils;
import com.aliyuncs.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.SysPaymentOrder;
import com.ruoyi.system.domain.vo.PaymentResult;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.service.PaymentService;

import com.ruoyi.xmbj.api.service.XmbjAuthService;
import com.ruoyi.xmbj.domain.ClientUser;
import com.ruoyi.xmbj.service.ClientUserService;
import com.ruoyi.xmbj.service.IAfSubscriptionPlansService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Api(value = "支付",tags = {"支付"})
@RestController
@RequestMapping("/api/payment")
public class PaymentController extends BaseController {

    private Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Value("${frontend.domain}")
    private String frontendDomain;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private XmbjAuthService xmbjAuthService;

    @Autowired
    private ClientUserService clientUserService;
    @Autowired
    private IAfSubscriptionPlansService iAfSubscriptionPlansService;

    @GetMapping("/wxConfig")
    public AjaxResult getWechatPayConfig(@RequestParam String url) {
        // 1. 获取当前页面的URL，用于生成签名
        // 2. 调用微信支付API生成签名
        // 3. 返回签名和配置参数
        Map<String, String> config = paymentService.getJsApiConfig(url);
        return AjaxResult.success(config);
    }

    @GetMapping("/wechat/openid")
    public AjaxResult getOpenid(@RequestParam String code) {
        JSONObject json = paymentService.getOpenid(code);
        if (json.containsKey("openid")) {
            return AjaxResult.success(json);
        } else {
            return AjaxResult.error("获取 openid 失败");
        }
    }

    /**
     * 创建支付订单
     */
    @PostMapping("/create")
    @ApiOperation("创建订单")
    public AjaxResult createPayment(
//            @RequestParam BigDecimal amount,
            @RequestParam String paymentType,
            @RequestParam String userInfo,
            @RequestParam(required = false) String productName,
            @RequestParam() String planId,
            @RequestParam(required = false) String billingType,
            @RequestParam(required = false) String addonId,
            @RequestParam(required = false) String openid,
            @RequestParam(required = false) String url,
            HttpServletRequest httpServletRequest) {
        try {
            // String userInfo =
            // tokenService.getUserInfoFromToken(httpServletRequest).toString();
            if (StringUtils.isEmpty(userInfo)) {
                return AjaxResult.error("用户未登录,请登录");
            }
//            SysUser user = userService.getUserByUserInfo(userInfo);
//            if (user == null) {
//                return AjaxResult.error("用户不存在");
//            }

            ClientUser clientUser = clientUserService.getClientUserByUuid(userInfo);
            if (clientUser == null) {
                return AjaxResult.error("小马笔记客户端用户不存在");
            }
            PaymentResult paymentResult = paymentService.createPayment( paymentType, null, clientUser, openid, url, planId,billingType, addonId,
                    httpServletRequest);
            return AjaxResult.success(paymentResult);
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询支付状态
     */
    @GetMapping("/status")
    @Anonymous
    public AjaxResult checkPaymentStatus(@RequestParam String orderNo) {
        String status = paymentService.checkPaymentStatus(orderNo);
        return AjaxResult.success(status);
    }

    @GetMapping(value = "/myPaymentList")
    @Anonymous
    public AjaxResult myPaymentList(@RequestHeader("X-Client-Authorization") String clientAuth
    ,@RequestParam("pageNum") Integer pageNum
    ) {
        return AjaxResult.success(() -> {
            startOrderPage("create_time",false);
            return paymentService.myPaymentList(clientAuth);
        });
    }

    /**
     * 支付宝回调接口
     */
    @PostMapping("/callback/alipay")
    @Anonymous
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

    // 新增支付宝同步回调处理（用户支付成功后跳转）
    @GetMapping("/return/alipay")
    @Anonymous
    public String alipayReturn(HttpServletRequest request) {
        try {
            log.info("支付宝同步回调开始处理，请求参数: {}", request.getQueryString());

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = StringUtils.join(values, ",");
                params.put(name, valueStr);
            }

            String outTradeNo = params.get("out_trade_no");
            log.info("支付宝同步回调处理，订单号: {}", outTradeNo);

            // 验签
            if (!paymentService.verifySign("alipay", outTradeNo, params)) {
                log.error("支付宝同步回调验签失败，订单号: {}", outTradeNo);
                return generateErrorHtml("签名验证失败");
            }

            SysPaymentOrder paymentOrder = paymentService.getPaymentOrder(outTradeNo);
            if (paymentOrder == null) {
                log.error("支付宝同步回调订单查询失败，订单号: {}", outTradeNo);
                return generateErrorHtml("订单不存在");
            }

            // 记录支付成功日志
            log.info("支付宝支付成功，订单号: {}, 金额: {}, 状态: {}",
                    outTradeNo, paymentOrder.getAmount(), paymentOrder.getStatus());

            // 构建前端跳转URL
            String redirectUrl = String.format(
                    frontendDomain
                            + "/price?orderNo=%s&amount=%s&paymentType=%s&productName=%s&payTime=%s&status=%s",
                    URLEncoder.encode(outTradeNo, StandardCharsets.UTF_8.name()),
                    paymentOrder.getAmount(),
                    URLEncoder.encode(paymentOrder.getPaymentType(), StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(paymentOrder.getProductName() != null ? paymentOrder.getProductName() : "",
                            StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(paymentOrder.getPayTime() != null ? paymentOrder.getPayTime().toString() : "",
                            StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(paymentOrder.getStatus(), StandardCharsets.UTF_8.name()));

            log.info("支付宝同步回调处理完成，跳转到: {}", redirectUrl);

            // 返回HTML页面自动跳转
            return generateAutoRedirectHtml(redirectUrl, "支付成功", "支付成功，正在跳转...");

        } catch (Exception e) {
            log.error("支付宝同步回调处理异常", e);
            return generateErrorHtml("系统异常");
        }
    }

    /**
     * 微信支付回调接口
     */
    @PostMapping("/callback/wechat")
    @Anonymous
    public String wechatCallback(HttpServletRequest request) {
        try {
            // 1. 读取请求body和header
            String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            Map<String, String> wechatParams = paymentService.getWechatParams(request);
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
            if (root.has("resource") && root.get("resource").has("out_trade_no")
                    && !root.get("resource").get("out_trade_no").isNull()) {
                return root.get("resource").get("out_trade_no").asText();
            }
            // 其他可能的嵌套或不同字段名可在此扩展
            return null;
        } catch (IOException e) {
            log.error("解析微信通知 JSON 异常", e);
            return null;
        }
    }

    /**
     * 生成自动跳转的HTML页面
     */
    private String generateAutoRedirectHtml(String redirectUrl, String title, String message) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>" + title + "</title>" +
                "    <meta http-equiv=\"refresh\" content=\"3;url=" + redirectUrl + "\">" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }" +
                "        .message { margin: 20px 0; font-size: 18px; color: #333; }" +
                "        .countdown { color: #666; font-size: 14px; }" +
                "        .link { color: #1890ff; text-decoration: none; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>" + title + "</h1>" +
                "    <div class=\"message\">" + message + "</div>" +
                "    <div class=\"countdown\">页面将在 <span id=\"countdown\">3</span> 秒后自动跳转...</div>" +
                "    <div>如果页面没有自动跳转，请 <a href=\"" + redirectUrl + "\" class=\"link\">点击这里</a></div>" +
                "    <script>" +
                "        var seconds = 3;" +
                "        function updateCountdown() {" +
                "            seconds--;" +
                "            document.getElementById('countdown').textContent = seconds;" +
                "            if (seconds <= 0) {" +
                "                window.location.href = '" + redirectUrl + "';" +
                "            }" +
                "        }" +
                "        setInterval(updateCountdown, 1000);" +
                "    </script>" +
                "</body>" +
                "</html>";
    }

    /**
     * 生成错误提示HTML页面
     */
    private String generateErrorHtml(String errorMessage) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>支付失败</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }" +
                "        .error { color: #ff4d4f; font-size: 18px; margin: 20px 0; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>支付失败</h1>" +
                "    <div class=\"error\">错误信息: " + errorMessage + "</div>" +
                "    <div>请返回重新尝试或联系客服</div>" +
                "</body>" +
                "</html>";
    }

}