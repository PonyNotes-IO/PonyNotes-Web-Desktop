package com.ruoyi.xmbj.mapper;

import com.ruoyi.xmbj.domain.AppVersion;
import java.util.List;

/**
 * 应用版本Mapper接口
 *
 * @author ruoyi
 */
public interface AppVersionMapper {
    /**
     * 查询应用版本
     *
     * @param id 应用版本主键
     * @return 应用版本
     */
    public AppVersion selectAppVersionById(Long id);

    /**
     * 查询应用版本列表
     *
     * @param appVersion 应用版本
     * @return 应用版本集合
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersion);

    /**
     * 新增应用版本
     *
     * @param appVersion 应用版本
     * @return 结果
     */
    public int insertAppVersion(AppVersion appVersion);

    /**
     * 修改应用版本
     *
     * @param appVersion 应用版本
     * @return 结果
     */
    public int updateAppVersion(AppVersion appVersion);

    /**
     * 删除应用版本
     *
     * @param id 应用版本主键
     * @return 结果
     */
    public int deleteAppVersionById(Long id);

    /**
     * 批量删除应用版本
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppVersionByIds(Long[] ids);

    /**
     * 查询所有活跃的应用版本
     *
     * @return 应用版本集合
     */
    public List<AppVersion> selectActiveAppVersions();
}