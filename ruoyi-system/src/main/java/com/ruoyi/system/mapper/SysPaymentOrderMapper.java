package com.ruoyi.system.mapper;

import java.sql.Date;

import com.ruoyi.system.domain.PaymentOrder;

import io.lettuce.core.dynamic.annotation.Param;

public interface SysPaymentOrderMapper {
    PaymentOrder selectByOrderNo(String orderNo);
    int updatePaymentStatus(@Param("orderNo") String orderNo, @Param("status") String status, @Param("payTime") Date payTime, @Param("updateTime") Date updateTime);
}
