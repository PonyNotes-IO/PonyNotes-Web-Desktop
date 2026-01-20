package com.ruoyi.xmbj.service;

import java.util.List;

import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.xmbj.domain.AppVersion;
import com.ruoyi.xmbj.mapper.AppVersionMapper;

/**
 * 应用版本Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    /**
     * 查询应用版本
     *
     * @param id 应用版本主键
     * @return 应用版本
     */
    @DataSource(DataSourceType.slave)
    public AppVersion selectAppVersionById(Long id) {
        return appVersionMapper.selectAppVersionById(id);
    }

    /**
     * 查询应用版本列表
     *
     * @param appVersion 应用版本
     * @return 应用版本集合
     */
    @DataSource(DataSourceType.slave)
    public List<AppVersion> selectAppVersionList(AppVersion appVersion) {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    /**
     * 新增应用版本
     *
     * @param appVersion 应用版本
     * @return 结果
     */
    @DataSource(DataSourceType.slave)
    public int insertAppVersion(AppVersion appVersion) {
        return appVersionMapper.insertAppVersion(appVersion);
    }

    /**
     * 修改应用版本
     *
     * @param appVersion 应用版本
     * @return 结果
     */
    @DataSource(DataSourceType.slave)
    public int updateAppVersion(AppVersion appVersion) {
        return appVersionMapper.updateAppVersion(appVersion);
    }

    /**
     * 批量删除应用版本
     *
     * @param ids 需要删除的应用版本主键集合
     * @return 结果
     */
    @DataSource(DataSourceType.slave)
    public int deleteAppVersionByIds(Long[] ids) {
        return appVersionMapper.deleteAppVersionByIds(ids);
    }

    /**
     * 删除应用版本信息
     *
     * @param id 应用版本主键
     * @return 结果
     */
    @DataSource(DataSourceType.slave)
    public int deleteAppVersionById(Long id) {
        return appVersionMapper.deleteAppVersionById(id);
    }

    /**
     * 停用应用版本
     *
     * @param id 应用版本主键
     * @return 结果
     */
    @DataSource(DataSourceType.slave)
    public int disableAppVersion(Long id) {
        AppVersion appVersion = appVersionMapper.selectAppVersionById(id);
        if (appVersion != null) {
            appVersion.setStatus("停用");
            return appVersionMapper.updateAppVersion(appVersion);
        }
        return 0;
    }

    /**
     * 启用应用版本
     *
     * @param id 应用版本主键
     * @return 结果
     */
    @DataSource(DataSourceType.slave)
    public int enableAppVersion(Long id) {
        AppVersion appVersion = appVersionMapper.selectAppVersionById(id);
        if (appVersion != null) {
            appVersion.setStatus("正常");
            return appVersionMapper.updateAppVersion(appVersion);
        }
        return 0;
    }

    /**
     * 查询所有活跃的应用版本
     *
     * @return 应用版本集合
     */
    @DataSource(DataSourceType.slave)
    public List<AppVersion> selectActiveAppVersions() {
        return appVersionMapper.selectActiveAppVersions();
    }
}