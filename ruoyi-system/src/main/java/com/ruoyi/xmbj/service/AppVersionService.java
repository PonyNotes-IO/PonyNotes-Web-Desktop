package com.ruoyi.xmbj.service;

import java.util.List;

import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.XmAppVersion;
import com.ruoyi.xmbj.mapper.XmAppVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.annotation.DataSource;

/**
 * 应用版本Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class AppVersionService {

    @Autowired
    private XmAppVersionMapper appVersionMapper;

    /**
     * 查询应用版本
     *
     * @param id 应用版本主键
     * @return 应用版本
     */
    @DataSource(DataSourceType.SLAVE)
    public XmAppVersion selectAppVersionById(Long id) {
        return appVersionMapper.selectAppVersionById(id);
    }

    /**
     * 查询应用版本列表
     *
     * @param appVersion 应用版本
     * @return 应用版本集合
     */
    @DataSource(DataSourceType.SLAVE)
    public List<XmAppVersion> selectAppVersionList(XmAppVersion appVersion) {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    /**
     * 新增应用版本
     *
     * @param appVersion 应用版本
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int insertAppVersion(XmAppVersion appVersion) {
        return appVersionMapper.insertAppVersion(appVersion);
    }

    /**
     * 修改应用版本
     *
     * @param appVersion 应用版本
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int updateAppVersion(XmAppVersion appVersion) {
        return appVersionMapper.updateAppVersion(appVersion);
    }

    /**
     * 批量删除应用版本
     *
     * @param ids 需要删除的应用版本主键集合
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int deleteAppVersionByIds(Long[] ids) {
        return appVersionMapper.deleteAppVersionByIds(ids);
    }

    /**
     * 删除应用版本信息
     *
     * @param id 应用版本主键
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int deleteAppVersionById(Long id) {
        return appVersionMapper.deleteAppVersionById(id);
    }

    /**
     * 停用应用版本
     *
     * @param id 应用版本主键
     * @return 结果
     */
    @DataSource(DataSourceType.SLAVE)
    public int disableAppVersion(Long id) {
        XmAppVersion appVersion = appVersionMapper.selectAppVersionById(id);
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
    @DataSource(DataSourceType.SLAVE)
    public int enableAppVersion(Long id) {
        XmAppVersion appVersion = appVersionMapper.selectAppVersionById(id);
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
    @DataSource(DataSourceType.SLAVE)
    public List<XmAppVersion> selectActiveAppVersions() {
        return appVersionMapper.selectActiveAppVersions();
    }
}