package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserLoginProfile;

/**
 * 用户登录概况Mapper接口
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public interface SysUserLoginProfileMapper 
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
     * 删除用户登录概况
     * 
     * @param profileId 用户登录概况主键
     * @return 结果
     */
    public int deleteSysUserLoginProfileByProfileId(Long profileId);

    /**
     * 批量删除用户登录概况
     * 
     * @param profileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserLoginProfileByProfileIds(Long[] profileIds);
}
