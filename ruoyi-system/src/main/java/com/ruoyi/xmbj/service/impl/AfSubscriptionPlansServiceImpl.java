package com.ruoyi.xmbj.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.mapper.AfSubscriptionPlansMapper;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.service.IAfSubscriptionPlansService;

/**
 * 订阅套餐Service业务层处理
 * 
 * @author 张继科
 * @date 2026-01-21
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class AfSubscriptionPlansServiceImpl implements IAfSubscriptionPlansService 
{
    @Autowired
    private AfSubscriptionPlansMapper afSubscriptionPlansMapper;

    /**
     * 查询订阅套餐
     * 
     * @param id 订阅套餐主键
     * @return 订阅套餐
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionPlans selectAfSubscriptionPlansById(Long id)
    {
        return afSubscriptionPlansMapper.selectAfSubscriptionPlansById(id);
    }

    /**
     * 查询订阅套餐列表
     * 
     * @param afSubscriptionPlans 订阅套餐
     * @return 订阅套餐
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionPlans> selectAfSubscriptionPlansList(AfSubscriptionPlans afSubscriptionPlans)
    {
        return afSubscriptionPlansMapper.selectAfSubscriptionPlansList(afSubscriptionPlans);
    }

    /**
     * 新增订阅套餐
     * 
     * @param afSubscriptionPlans 订阅套餐
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int insertAfSubscriptionPlans(AfSubscriptionPlans afSubscriptionPlans)
    {
        return afSubscriptionPlansMapper.insertAfSubscriptionPlans(afSubscriptionPlans);
    }

    /**
     * 修改订阅套餐
     * 
     * @param afSubscriptionPlans 订阅套餐
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int updateAfSubscriptionPlans(AfSubscriptionPlans afSubscriptionPlans)
    {
         int result = afSubscriptionPlansMapper.updateAfSubscriptionPlans(afSubscriptionPlans);
        return result;
    }

    /**
     * 批量删除订阅套餐
     * 
     * @param ids 需要删除的订阅套餐主键
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfSubscriptionPlansByIds(Long[] ids)
    {
        return afSubscriptionPlansMapper.deleteAfSubscriptionPlansByIds(ids);
    }

    /**
     * 删除订阅套餐信息
     * 
     * @param id 订阅套餐主键
     * @return 结果
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfSubscriptionPlansById(Long id)
    {
        return afSubscriptionPlansMapper.deleteAfSubscriptionPlansById(id);
    }

    /**
     * 检查订阅套餐是否被用户订阅
     * 
     * @param id 订阅套餐主键
     * @return 订阅数量
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public int countUserSubscriptionsByPlanId(Long id)
    {
        return afSubscriptionPlansMapper.countUserSubscriptionsByPlanId(id);
    }

    @Override
    public BigDecimal getAmountByIdAndBliingType(String planId, String billingType) {
        AfSubscriptionPlans plan = afSubscriptionPlansMapper.selectById(Long.valueOf(planId));
        if(plan == null) throw new RuntimeException("参数错误,planId不存在");

        return "0".equals(billingType) ? plan.getMonthlyPriceYuan(): plan.getYearlyPriceYuan();// null;
    }
}
