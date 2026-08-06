package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DownloadLink;

/**
 * 下载链接配置Service接口
 *
 * @author 张继科
 * @date 2026-08-05
 */
public interface IDownloadLinkService
{
    /**
     * 查询下载链接
     *
     * @param id 下载链接主键
     * @return 下载链接
     */
    public DownloadLink selectDownloadLinkById(Long id);

    /**
     * 查询下载链接列表
     *
     * @param downloadLink 下载链接
     * @return 下载链接集合
     */
    public List<DownloadLink> selectDownloadLinkList(DownloadLink downloadLink);

    /**
     * 新增下载链接
     *
     * @param downloadLink 下载链接
     * @return 结果
     */
    public int insertDownloadLink(DownloadLink downloadLink);

    /**
     * 修改下载链接
     *
     * @param downloadLink 下载链接
     * @return 结果
     */
    public int updateDownloadLink(DownloadLink downloadLink);

    /**
     * 批量删除下载链接
     *
     * @param ids 需要删除的下载链接主键集合
     * @return 结果
     */
    public int deleteDownloadLinkByIds(Long[] ids);

    /**
     * 删除下载链接信息
     *
     * @param id 下载链接主键
     * @return 结果
     */
    public int deleteDownloadLinkById(Long id);
}
