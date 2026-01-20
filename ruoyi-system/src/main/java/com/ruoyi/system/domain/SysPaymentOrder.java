package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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

    private String userId;
    private String ClientUserId; // 客户端用户ID
    private String ClientSubscriptionId; // 订阅订单ID
    private String ClientUserAddonId; // 补充包订单ID
    private String planId;
    private String addonId;

    private String billingType;

    private Integer quantity;

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