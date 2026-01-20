package com.ruoyi.system.domain.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PaymentOrderVo {
//订单号
//产品名称
//订单总金额
//支付方式
//支付状态
    private Long id;
    private String orderNo; // 订单编号
    private BigDecimal amount; // 支付金额
    private String paymentType; // 支付方式：wechat/alipay
    private String qrCodeUrl; // 二维码URL
    private String status; // 状态：pending/success/failed
    private Date createTime;
    private Date updateTime;
    private Date payTime;
    private String userId;
    private String userInfo;
    private String productName; // 商品名称
    private String ClientUserId; // 客户端用户ID
    private String ClientSubscriptionId; // 订阅订单ID
    private String ClientUserAddonId; // 补充包订单ID
    private String planId;
    private String addonId;
    private String billingType;
    private Integer quantity;
    /** 订阅状态 */
    private String afstatus;

    /** 订阅开通时间 */
    private LocalDateTime startDate;

    /** 订阅到期时间 */
    private LocalDateTime endDate;
}
