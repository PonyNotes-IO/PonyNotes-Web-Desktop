package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUserLoginRecordMapper;
import com.ruoyi.system.domain.SysUserLoginRecord;
import com.ruoyi.system.service.ISysUserLoginRecordService;

/**
 * 用户登录记录Service业务层处理
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
@Service
public class SysUserLoginRecordServiceImpl implements ISysUserLoginRecordService 
{
    @Autowired
    private SysUserLoginRecordMapper sysUserLoginRecordMapper;

    /**
     * 查询用户登录记录
     * 
     * @param recordId 用户登录记录主键
     * @return 用户登录记录
     */
    @Override
    public SysUserLoginRecord selectSysUserLoginRecordByRecordId(Long recordId)
    {
        return sysUserLoginRecordMapper.selectSysUserLoginRecordByRecordId(recordId);
    }

    /**
     * 查询用户登录记录列表
     * 
     * @param sysUserLoginRecord 用户登录记录
     * @return 用户登录记录
     */
    @Override
    public List<SysUserLoginRecord> selectSysUserLoginRecordList(SysUserLoginRecord sysUserLoginRecord)
    {
        return sysUserLoginRecordMapper.selectSysUserLoginRecordList(sysUserLoginRecord);
    }

    /**
     * 新增用户登录记录
     * 
     * @param sysUserLoginRecord 用户登录记录
     * @return 结果
     */
    @Override
    public int insertSysUserLoginRecord(SysUserLoginRecord sysUserLoginRecord)
    {
        return sysUserLoginRecordMapper.insertSysUserLoginRecord(sysUserLoginRecord);
    }

    /**
     * 修改用户登录记录
     * 
     * @param sysUserLoginRecord 用户登录记录
     * @return 结果
     */
    @Override
    public int updateSysUserLoginRecord(SysUserLoginRecord sysUserLoginRecord)
    {
        return sysUserLoginRecordMapper.updateSysUserLoginRecord(sysUserLoginRecord);
    }

    /**
     * 批量删除用户登录记录
     * 
     * @param recordIds 需要删除的用户登录记录主键
     * @return 结果
     */
    @Override
    public int deleteSysUserLoginRecordByRecordIds(Long[] recordIds)
    {
        return sysUserLoginRecordMapper.deleteSysUserLoginRecordByRecordIds(recordIds);
    }

    /**
     * 删除用户登录记录信息
     * 
     * @param recordId 用户登录记录主键
     * @return 结果
     */
    @Override
    public int deleteSysUserLoginRecordByRecordId(Long recordId)
    {
        return sysUserLoginRecordMapper.deleteSysUserLoginRecordByRecordId(recordId);
    }
}
