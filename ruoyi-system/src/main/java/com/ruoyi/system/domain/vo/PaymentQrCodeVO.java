package com.ruoyi.system.domain.vo;


import java.util.Date;


public class PaymentQrCodeVO {
    private String orderNo; // 订单编号
    private String qrCodeUrl; // 二维码URL
    private Date expireTime; // 过期时间

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}