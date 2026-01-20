package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单 sys_payment_order
 * 
 * @author ruoyi
 */
public class SysPaymentOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @Excel(name = "订单ID", cellType = ColumnType.NUMERIC)
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 支付金额(元) */
    @Excel(name = "支付金额", cellType = ColumnType.NUMERIC)
    private BigDecimal amount;

    /** 支付方式(wechat:微信,alipay:支付宝) */
    @Excel(name = "支付方式", readConverterExp = "wechat=微信,alipay=支付宝")
    private String paymentType;

    /** 支付二维码URL */
    @Excel(name = "支付二维码URL")
    private String qrCodeUrl;

    /** 订单状态(pending:待支付,success:已支付,failed:失败) */
    @Excel(name = "订单状态", readConverterExp = "pending=待支付,success=已支付,failed=失败")
    private String status;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 支付时间 */
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    private String userInfo;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
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

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
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
            .append("productName", getProductName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("payTime", getPayTime())
            .toString();
    }
}