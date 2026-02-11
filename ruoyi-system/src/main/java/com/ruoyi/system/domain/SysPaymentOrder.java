package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 支付订单对象 sys_payment_order
 * 
 * @author 张继科
 * @date 2026-01-21
 */
public class SysPaymentOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 支付金额(元) */
    @Excel(name = "支付金额(元)")
    private BigDecimal amount;

    /** 支付方式(wechat:微信,alipay:支付宝) */
    @Excel(name = "支付方式(wechat:微信,alipay:支付宝)")
    private String paymentType;

    /** 支付二维码URL */
    @Excel(name = "支付二维码URL")
    private String qrCodeUrl;

    /** 订单状态(pending:待支付,success:已支付,failed:失败) */
    @Excel(name = "订单状态(pending:待支付,success:已支付,failed:失败)")
    private String status;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 支付开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startPayTime;

    /** 支付结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endPayTime;

    /**  */
    @Excel(name = "")
    private String productName;

    /**  */
    @Excel(name = "")
    private String userInfo;

    /** 开通时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开通时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String addonId;
    private String planId;

    private String userId;
    private String clientUserId;
    private String billingType;
    private Integer quantity;
    private String clientUserAddonId;
    private String clientSubscriptionId;

    private Date memberExpireTime;

    public Date getMemberExpireTime() {
        return memberExpireTime;
    }

    public void setMemberExpireTime(Date memberExpireTime) {
        this.memberExpireTime = memberExpireTime;
    }

    public String getClientSubscriptionId() {
        return clientSubscriptionId;
    }

    public void setClientSubscriptionId(String clientSubscriptionId) {
        this.clientSubscriptionId = clientSubscriptionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
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

    public String getClientUserAddonId() {
        return clientUserAddonId;
    }

    public void setClientUserAddonId(String clientUserAddonId) {
        this.clientUserAddonId = clientUserAddonId;
    }

    public String getAddonId() {
        return addonId;
    }

    public void setAddonId(String addonId) {
        this.addonId = addonId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    public void setPaymentType(String paymentType) 
    {
        this.paymentType = paymentType;
    }

    public String getPaymentType() 
    {
        return paymentType;
    }

    public void setQrCodeUrl(String qrCodeUrl) 
    {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrCodeUrl() 
    {
        return qrCodeUrl;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }

    public void setStartPayTime(Date startPayTime) 
    {
        this.startPayTime = startPayTime;
    }

    public Date getStartPayTime() 
    {
        return startPayTime;
    }

    public void setEndPayTime(Date endPayTime) 
    {
        this.endPayTime = endPayTime;
    }

    public Date getEndPayTime() 
    {
        return endPayTime;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setUserInfo(String userInfo) 
    {
        this.userInfo = userInfo;
    }

    public String getUserInfo() 
    {
        return userInfo;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("amount", getAmount())
            .append("paymentType", getPaymentType())
            .append("qrCodeUrl", getQrCodeUrl())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("payTime", getPayTime())
            .append("startPayTime", getStartPayTime())
            .append("endPayTime", getEndPayTime())
            .append("productName", getProductName())
            .append("userInfo", getUserInfo())
            .toString();
    }
}
