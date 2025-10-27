package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.PaymentOrder;
import com.ruoyi.system.domain.vo.PaymentQrCodeVO;
import com.ruoyi.system.mapper.PaymentOrderMapper;
//import com.ruoyi.system.service.PaymentService;
import com.ruoyi.system.service.SysPaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class SysPaymentServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements SysPaymentService {

    /**
     * 创建支付订单并生成二维码
     */
    @Override
    public PaymentQrCodeVO createPayment(BigDecimal amount, String paymentType) {
        // 1. 生成唯一订单号
        String orderNo = generateOrderNo(paymentType);

        // 2. 模拟调用微信/支付宝SDK生成二维码（实际项目替换为真实SDK调用）
        String qrCodeUrl = generateQrCodeUrl(orderNo, paymentType, amount);

        // 3. 保存订单
        PaymentOrder order = new PaymentOrder();
        order.setOrderNo(orderNo);
        order.setAmount(amount);
        order.setPaymentType(paymentType);
        order.setQrCodeUrl(qrCodeUrl);
        order.setStatus("pending"); // 初始状态：待支付
        order.setCreateTime(new Date());
        baseMapper.insert(order);

        // 4. 返回二维码信息
        PaymentQrCodeVO vo = new PaymentQrCodeVO();
        vo.setOrderNo(orderNo);
        vo.setQrCodeUrl(qrCodeUrl);
        vo.setExpireTime(DateUtils.addMinutes(new Date(), 15)); // 15分钟过期
        return vo;
    }

    /**
     * 查询支付状态
     */
    @Override
    public String checkPaymentStatus(String orderNo) {
        PaymentOrder order = baseMapper.selectOne(
                new QueryWrapper<PaymentOrder>().eq("order_no", orderNo)
        );
        return order != null ? order.getStatus() : "invalid";
    }

    /**
     * 处理支付回调（更新订单状态为已支付）
     */
    @Override
    public boolean handlePaymentCallback(String orderNo, String paymentType) {
        PaymentOrder order = baseMapper.selectOne(
                new QueryWrapper<PaymentOrder>().eq("order_no", orderNo)
        );
        if (order == null || !"pending".equals(order.getStatus())) {
            return false;
        }

        // 更新订单状态
        order.setStatus("success");
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());
        return baseMapper.updateById(order) > 0;
    }

    /**
     * 生成订单号（规则：支付方式_时间戳_随机数）
     */
    private String generateOrderNo(String paymentType) {
        return paymentType + "_" + System.currentTimeMillis() + "_" + 
                UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 模拟生成支付二维码URL（实际项目替换为真实支付平台接口调用）
     * 微信支付：调用微信统一下单接口获取prepay_id，生成二维码
     * 支付宝：调用支付宝交易创建接口获取payUrl
     */
    private String generateQrCodeUrl(String orderNo, String paymentType, BigDecimal amount) {
        if ("wechat".equals(paymentType)) {
            // 微信支付二维码（模拟）
            return "https://api.weixin.qq.com/pay/qrcode?orderNo=" + orderNo + "&amount=" + amount;
        } else if ("alipay".equals(paymentType)) {
            // 支付宝支付二维码（模拟）
            return "https://openapi.alipay.com/gateway.do?out_trade_no=" + orderNo + "&total_amount=" + amount;
        }
        throw new IllegalArgumentException("不支持的支付方式：" + paymentType);
    }
}