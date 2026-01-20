package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AppVersion;

/**
 * App版本管理Mapper接口
 * 
 * @author 张继科
 * @date 2026-01-25
 */
public interface AppVersionMapper 
{
    /**
     * 查询App版本管理
     * 
     * @param id App版本管理主键
     * @return App版本管理
     */
    public AppVersion selectAppVersionById(Long id);

    /**
     * 查询App版本管理列表
     * 
     * @param appVersion App版本管理
     * @return App版本管理集合
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersion);

    /**
     * 新增App版本管理
     * 
     * @param appVersion App版本管理
     * @return 结果
     */
    public int insertAppVersion(AppVersion appVersion);

    /**
     * 修改App版本管理
     * 
     * @param appVersion App版本管理
     * @return 结果
     */
    public int updateAppVersion(AppVersion appVersion);

    /**
     * 删除App版本管理
     * 
     * @param id App版本管理主键
     * @return 结果
     */
    public int deleteAppVersionById(Long id);

    /**
     * 批量删除App版本管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppVersionByIds(Long[] ids);
}
