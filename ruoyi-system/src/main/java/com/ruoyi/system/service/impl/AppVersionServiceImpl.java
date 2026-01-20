package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppVersionMapper;
import com.ruoyi.system.domain.AppVersion;
import com.ruoyi.system.service.IAppVersionService;

/**
 * App版本管理Service业务层处理
 * 
 * @author 张继科
 * @date 2026-01-25
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService 
{
    @Autowired
    private AppVersionMapper appVersionMapper;

    /**
     * 查询App版本管理
     * 
     * @param id App版本管理主键
     * @return App版本管理
     */
    @Override
    public AppVersion selectAppVersionById(Long id)
    {
        return appVersionMapper.selectAppVersionById(id);
    }

    /**
     * 查询App版本管理列表
     * 
     * @param appVersion App版本管理
     * @return App版本管理
     */
    @Override
    public List<AppVersion> selectAppVersionList(AppVersion appVersion)
    {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    /**
     * 新增App版本管理
     * 
     * @param appVersion App版本管理
     * @return 结果
     */
    @Override
    public int insertAppVersion(AppVersion appVersion)
    {
        return appVersionMapper.insertAppVersion(appVersion);
    }

    /**
     * 修改App版本管理
     * 
     * @param appVersion App版本管理
     * @return 结果
     */
    @Override
    public int updateAppVersion(AppVersion appVersion)
    {
        return appVersionMapper.updateAppVersion(appVersion);
    }

    /**
     * 批量删除App版本管理
     * 
     * @param ids 需要删除的App版本管理主键
     * @return 结果
     */
    @Override
    public int deleteAppVersionByIds(Long[] ids)
    {
        return appVersionMapper.deleteAppVersionByIds(ids);
    }

    /**
     * 删除App版本管理信息
     * 
     * @param id App版本管理主键
     * @return 结果
     */
    @Override
    public int deleteAppVersionById(Long id)
    {
        return appVersionMapper.deleteAppVersionById(id);
    }
}
