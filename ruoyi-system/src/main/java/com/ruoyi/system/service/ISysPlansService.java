package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysPlans;

/**
 * 套餐管理 业务层
 * 
 * @author ruoyi
 */
public interface ISysPlansService
{
    /**
     * 查询套餐列表
     * 
     * @param sysPlans 套餐信息
     * @return 套餐集合
     */
    public List<SysPlans> selectPlansList(SysPlans sysPlans);

    /**
     * 根据套餐ID查询套餐信息
     * 
     * @param id 套餐ID
     * @return 套餐信息
     */
    public SysPlans selectPlansById(Long id);

    /**
     * 新增套餐
     * 
     * @param sysPlans 套餐信息
     * @return 结果
     */
    public int insertPlans(SysPlans sysPlans);

    /**
     * 修改套餐
     * 
     * @param sysPlans 套餐信息
     * @return 结果
     */
    public int updatePlans(SysPlans sysPlans);

    /**
     * 根据套餐ID删除套餐
     * 
     * @param id 套餐ID
     * @return 结果
     */
    public int deletePlansById(Long id);

    /**
     * 批量删除套餐
     * 
     * @param ids 需要删除的套餐ID集合
     * @return 结果
     */
    public int deletePlansByIds(Long[] ids);
}