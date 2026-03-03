package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentListDTO {


    private Date payTime;
    private Double amount;
    private String productName;
    private Date createTime;
    private String billingType;
    private String paymentType;
    private String status;


}
