package com.ruoyi.xmbj.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface WechatAppFulfillmentMapper {
    @Insert("INSERT INTO wechat_app_fulfillment(order_no) VALUES(#{orderNo}) ON CONFLICT DO NOTHING")
    int claim(String orderNo);

    @Select("SELECT subscription_id FROM wechat_app_fulfillment WHERE order_no = #{orderNo}")
    Long subscriptionId(String orderNo);

    @Update("UPDATE wechat_app_fulfillment SET subscription_id = #{subscriptionId} WHERE order_no = #{orderNo}")
    int complete(@Param("orderNo") String orderNo, @Param("subscriptionId") Long subscriptionId);
}
