package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUserThirdBindMapper;
import com.ruoyi.system.domain.SysUserThirdBind;
import com.ruoyi.system.service.ISysUserThirdBindService;

/**
 * 第三方登录绑定Service业务层处理
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
@Service
public class SysUserThirdBindServiceImpl implements ISysUserThirdBindService 
{
    @Autowired
    private SysUserThirdBindMapper sysUserThirdBindMapper;

    /**
     * 查询第三方登录绑定
     * 
     * @param bindId 第三方登录绑定主键
     * @return 第三方登录绑定
     */
    @Override
    public SysUserThirdBind selectSysUserThirdBindByBindId(Long bindId)
    {
        return sysUserThirdBindMapper.selectSysUserThirdBindByBindId(bindId);
    }

    /**
     * 查询第三方登录绑定列表
     * 
     * @param sysUserThirdBind 第三方登录绑定
     * @return 第三方登录绑定
     */
    @Override
    public List<SysUserThirdBind> selectSysUserThirdBindList(SysUserThirdBind sysUserThirdBind)
    {
        return sysUserThirdBindMapper.selectSysUserThirdBindList(sysUserThirdBind);
    }

    /**
     * 新增第三方登录绑定
     * 
     * @param sysUserThirdBind 第三方登录绑定
     * @return 结果
     */
    @Override
    public int insertSysUserThirdBind(SysUserThirdBind sysUserThirdBind)
    {
        return sysUserThirdBindMapper.insertSysUserThirdBind(sysUserThirdBind);
    }

    /**
     * 修改第三方登录绑定
     * 
     * @param sysUserThirdBind 第三方登录绑定
     * @return 结果
     */
    @Override
    public int updateSysUserThirdBind(SysUserThirdBind sysUserThirdBind)
    {
        return sysUserThirdBindMapper.updateSysUserThirdBind(sysUserThirdBind);
    }

    /**
     * 批量删除第三方登录绑定
     * 
     * @param bindIds 需要删除的第三方登录绑定主键
     * @return 结果
     */
    @Override
    public int deleteSysUserThirdBindByBindIds(Long[] bindIds)
    {
        return sysUserThirdBindMapper.deleteSysUserThirdBindByBindIds(bindIds);
    }

    /**
     * 删除第三方登录绑定信息
     * 
     * @param bindId 第三方登录绑定主键
     * @return 结果
     */
    @Override
    public int deleteSysUserThirdBindByBindId(Long bindId)
    {
        return sysUserThirdBindMapper.deleteSysUserThirdBindByBindId(bindId);
    }
}
