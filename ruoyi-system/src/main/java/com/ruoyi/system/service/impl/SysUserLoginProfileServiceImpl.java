package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUserLoginProfileMapper;
import com.ruoyi.system.domain.SysUserLoginProfile;
import com.ruoyi.system.service.ISysUserLoginProfileService;

/**
 * 用户登录概况Service业务层处理
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
@Service
public class SysUserLoginProfileServiceImpl implements ISysUserLoginProfileService 
{
    @Autowired
    private SysUserLoginProfileMapper sysUserLoginProfileMapper;

    /**
     * 查询用户登录概况
     * 
     * @param profileId 用户登录概况主键
     * @return 用户登录概况
     */
    @Override
    public SysUserLoginProfile selectSysUserLoginProfileByProfileId(Long profileId)
    {
        return sysUserLoginProfileMapper.selectSysUserLoginProfileByProfileId(profileId);
    }

    /**
     * 查询用户登录概况列表
     * 
     * @param sysUserLoginProfile 用户登录概况
     * @return 用户登录概况
     */
    @Override
    public List<SysUserLoginProfile> selectSysUserLoginProfileList(SysUserLoginProfile sysUserLoginProfile)
    {
        return sysUserLoginProfileMapper.selectSysUserLoginProfileList(sysUserLoginProfile);
    }

    /**
     * 新增用户登录概况
     * 
     * @param sysUserLoginProfile 用户登录概况
     * @return 结果
     */
    @Override
    public int insertSysUserLoginProfile(SysUserLoginProfile sysUserLoginProfile)
    {
        sysUserLoginProfile.setCreateTime(DateUtils.getNowDate());
        return sysUserLoginProfileMapper.insertSysUserLoginProfile(sysUserLoginProfile);
    }

    /**
     * 修改用户登录概况
     * 
     * @param sysUserLoginProfile 用户登录概况
     * @return 结果
     */
    @Override
    public int updateSysUserLoginProfile(SysUserLoginProfile sysUserLoginProfile)
    {
        return sysUserLoginProfileMapper.updateSysUserLoginProfile(sysUserLoginProfile);
    }

    /**
     * 批量删除用户登录概况
     * 
     * @param profileIds 需要删除的用户登录概况主键
     * @return 结果
     */
    @Override
    public int deleteSysUserLoginProfileByProfileIds(Long[] profileIds)
    {
        return sysUserLoginProfileMapper.deleteSysUserLoginProfileByProfileIds(profileIds);
    }

    /**
     * 删除用户登录概况信息
     * 
     * @param profileId 用户登录概况主键
     * @return 结果
     */
    @Override
    public int deleteSysUserLoginProfileByProfileId(Long profileId)
    {
        return sysUserLoginProfileMapper.deleteSysUserLoginProfileByProfileId(profileId);
    }
}
