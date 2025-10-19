package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sys_payment_order")
public class PaymentOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo; // 订单编号
    private BigDecimal amount; // 支付金额
    private String paymentType; // 支付方式：wechat/alipay
    private String qrCodeUrl; // 二维码URL
    private String status; // 状态：pending/success/failed
    private Date createTime;
    private Date updateTime;
    private Date payTime;
}