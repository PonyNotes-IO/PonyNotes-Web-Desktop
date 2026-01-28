package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserLoginRecord;

/**
 * 用户登录记录Mapper接口
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public interface SysUserLoginRecordMapper 
{
    /**
     * 查询用户登录记录
     * 
     * @param recordId 用户登录记录主键
     * @return 用户登录记录
     */
    public SysUserLoginRecord selectSysUserLoginRecordByRecordId(Long recordId);

    /**
     * 查询用户登录记录列表
     * 
     * @param sysUserLoginRecord 用户登录记录
     * @return 用户登录记录集合
     */
    public List<SysUserLoginRecord> selectSysUserLoginRecordList(SysUserLoginRecord sysUserLoginRecord);

    /**
     * 新增用户登录记录
     * 
     * @param sysUserLoginRecord 用户登录记录
     * @return 结果
     */
    public int insertSysUserLoginRecord(SysUserLoginRecord sysUserLoginRecord);

    /**
     * 修改用户登录记录
     * 
     * @param sysUserLoginRecord 用户登录记录
     * @return 结果
     */
    public int updateSysUserLoginRecord(SysUserLoginRecord sysUserLoginRecord);

    /**
     * 删除用户登录记录
     * 
     * @param recordId 用户登录记录主键
     * @return 结果
     */
    public int deleteSysUserLoginRecordByRecordId(Long recordId);

    /**
     * 批量删除用户登录记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserLoginRecordByRecordIds(Long[] recordIds);
}
