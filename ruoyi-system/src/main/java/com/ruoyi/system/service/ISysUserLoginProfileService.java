package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysUserLoginProfile;

/**
 * 用户登录概况Service接口
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public interface ISysUserLoginProfileService 
{
    /**
     * 查询用户登录概况
     * 
     * @param profileId 用户登录概况主键
     * @return 用户登录概况
     */
    public SysUserLoginProfile selectSysUserLoginProfileByProfileId(Long profileId);

    /**
     * 查询用户登录概况列表
     * 
     * @param sysUserLoginProfile 用户登录概况
     * @return 用户登录概况集合
     */
    public List<SysUserLoginProfile> selectSysUserLoginProfileList(SysUserLoginProfile sysUserLoginProfile);

    /**
     * 新增用户登录概况
     * 
     * @param sysUserLoginProfile 用户登录概况
     * @return 结果
     */
    public int insertSysUserLoginProfile(SysUserLoginProfile sysUserLoginProfile);

    /**
     * 修改用户登录概况
     * 
     * @param sysUserLoginProfile 用户登录概况
     * @return 结果
     */
    public int updateSysUserLoginProfile(SysUserLoginProfile sysUserLoginProfile);

    /**
     * 批量删除用户登录概况
     * 
     * @param profileIds 需要删除的用户登录概况主键集合
     * @return 结果
     */
    public int deleteSysUserLoginProfileByProfileIds(Long[] profileIds);

    /**
     * 删除用户登录概况信息
     * 
     * @param profileId 用户登录概况主键
     * @return 结果
     */
    public int deleteSysUserLoginProfileByProfileId(Long profileId);
}
