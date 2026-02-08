package com.ruoyi.xmbj.service;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;

/**
 * 订阅套餐Service接口
 * 
 * @author 张继科
 * @date 2026-01-21
 */
public interface IAfSubscriptionPlansService 
{
    /**
     * 查询订阅套餐
     * 
     * @param id 订阅套餐主键
     * @return 订阅套餐
     */
    public AfSubscriptionPlans selectAfSubscriptionPlansById(Long id);

    /**
     * 查询订阅套餐列表
     * 
     * @param afSubscriptionPlans 订阅套餐
     * @return 订阅套餐集合
     */
    public List<AfSubscriptionPlans> selectAfSubscriptionPlansList(AfSubscriptionPlans afSubscriptionPlans);

    /**
     * 新增订阅套餐
     * 
     * @param afSubscriptionPlans 订阅套餐
     * @return 结果
     */
    public int insertAfSubscriptionPlans(AfSubscriptionPlans afSubscriptionPlans);

    /**
     * 修改订阅套餐
     * 
     * @param afSubscriptionPlans 订阅套餐
     * @return 结果
     */
    public int updateAfSubscriptionPlans(AfSubscriptionPlans afSubscriptionPlans);

    /**
     * 批量删除订阅套餐
     * 
     * @param ids 需要删除的订阅套餐主键集合
     * @return 结果
     */
    public int deleteAfSubscriptionPlansByIds(Long[] ids);

    /**
     * 删除订阅套餐信息
     * 
     * @param id 订阅套餐主键
     * @return 结果
     */
    public int deleteAfSubscriptionPlansById(Long id);

    /**
     * 检查订阅套餐是否被用户订阅
     * 
     * @param id 订阅套餐主键
     * @return 订阅数量
     */
    public int countUserSubscriptionsByPlanId(Long id);

    BigDecimal getAmountByIdAndBliingType(String planId, String billingType);
}
