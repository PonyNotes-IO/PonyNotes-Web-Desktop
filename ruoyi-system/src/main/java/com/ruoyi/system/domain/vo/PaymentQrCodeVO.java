package com.ruoyi.system.domain.vo;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentQrCodeVO {
    private String orderNo; // 订单编号
    private String qrCodeUrl; // 二维码URL
    private Date expireTime; // 过期时间
}