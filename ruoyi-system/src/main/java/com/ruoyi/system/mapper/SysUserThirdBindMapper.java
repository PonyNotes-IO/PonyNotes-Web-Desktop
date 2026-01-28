package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserThirdBind;

/**
 * 第三方登录绑定Mapper接口
 * 
 * @author zhangjike
 * @date 2026-01-21
 */
public interface SysUserThirdBindMapper 
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
     * 删除第三方登录绑定
     * 
     * @param bindId 第三方登录绑定主键
     * @return 结果
     */
    public int deleteSysUserThirdBindByBindId(Long bindId);

    /**
     * 批量删除第三方登录绑定
     * 
     * @param bindIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserThirdBindByBindIds(Long[] bindIds);
}
