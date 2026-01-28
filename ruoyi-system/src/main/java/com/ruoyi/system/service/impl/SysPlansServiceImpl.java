package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysPlansMapper;
import com.ruoyi.system.domain.SysPlans;
import com.ruoyi.system.service.ISysPlansService;

/**
 * 套餐管理 服务实现
 * 
 * @author ruoyi
 */
@Service
public class SysPlansServiceImpl implements ISysPlansService
{
    @Autowired
    private SysPlansMapper sysPlansMapper;

    /**
     * 查询套餐列表
     */
    @Override
    public List<SysPlans> selectPlansList(SysPlans sysPlans)
    {
        return sysPlansMapper.selectPlansList(sysPlans);
    }

    /**
     * 根据套餐ID查询套餐信息
     */
    @Override
    public SysPlans selectPlansById(Long id)
    {
        return sysPlansMapper.selectPlansById(id);
    }

    /**
     * 新增套餐
     */
    @Override
    public int insertPlans(SysPlans sysPlans)
    {
        return sysPlansMapper.insertPlans(sysPlans);
    }

    /**
     * 修改套餐
     */
    @Override
    public int updatePlans(SysPlans sysPlans)
    {
        return sysPlansMapper.updatePlans(sysPlans);
    }

    /**
     * 根据套餐ID删除套餐
     */
    @Override
    public int deletePlansById(Long id)
    {
        return sysPlansMapper.deletePlansById(id);
    }

    /**
     * 批量删除套餐
     */
    @Override
    public int deletePlansByIds(Long[] ids)
    {
        return sysPlansMapper.deletePlansByIds(ids);
    }
}