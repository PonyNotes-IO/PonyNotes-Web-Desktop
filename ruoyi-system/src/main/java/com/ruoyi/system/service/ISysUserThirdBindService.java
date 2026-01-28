package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysUserThirdBind;

/**
 * 第三方登录绑定Service接口
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public interface ISysUserThirdBindService 
{
    /**
     * 查询第三方登录绑定
     * 
     * @param bindId 第三方登录绑定主键
     * @return 第三方登录绑定
     */
    public SysUserThirdBind selectSysUserThirdBindByBindId(Long bindId);

    /**
     * 查询第三方登录绑定列表
     * 
     * @param sysUserThirdBind 第三方登录绑定
     * @return 第三方登录绑定集合
     */
    public List<SysUserThirdBind> selectSysUserThirdBindList(SysUserThirdBind sysUserThirdBind);

    /**
     * 新增第三方登录绑定
     * 
     * @param sysUserThirdBind 第三方登录绑定
     * @return 结果
     */
    public int insertSysUserThirdBind(SysUserThirdBind sysUserThirdBind);

    /**
     * 修改第三方登录绑定
     * 
     * @param sysUserThirdBind 第三方登录绑定
     * @return 结果
     */
    public int updateSysUserThirdBind(SysUserThirdBind sysUserThirdBind);

    /**
     * 批量删除第三方登录绑定
     * 
     * @param bindIds 需要删除的第三方登录绑定主键集合
     * @return 结果
     */
    public int deleteSysUserThirdBindByBindIds(Long[] bindIds);

    /**
     * 删除第三方登录绑定信息
     * 
     * @param bindId 第三方登录绑定主键
     * @return 结果
     */
    public int deleteSysUserThirdBindByBindId(Long bindId);
}
