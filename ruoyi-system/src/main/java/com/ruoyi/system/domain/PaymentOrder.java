package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentOrder {
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getClientUserId() {
        return ClientUserId;
    }
    public void setClientUserId(String clientUserId) {
        ClientUserId = clientUserId;
    }
    public String getClientSubscriptionId() {
        return ClientSubscriptionId;
    }
    public void setClientSubscriptionId(String clientSubscriptionId) {
        ClientSubscriptionId = clientSubscriptionId;
    }
    public String getClientUserAddonId() {
        return ClientUserAddonId;
    }
    public void setClientUserAddonId(String clientUserAddonId) {
        ClientUserAddonId = clientUserAddonId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getAddonId() {
        return addonId;
    }

    public void setAddonId(String addonId) {
        this.addonId = addonId;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}