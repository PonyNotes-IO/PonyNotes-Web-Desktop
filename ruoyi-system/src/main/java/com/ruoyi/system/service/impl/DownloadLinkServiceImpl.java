package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DownloadLinkMapper;
import com.ruoyi.system.domain.DownloadLink;
import com.ruoyi.system.service.IDownloadLinkService;

/**
 * 下载链接配置Service业务层实现
 *
 * @author 张继科
 * @date 2026-08-05
 */
@Service
public class DownloadLinkServiceImpl implements IDownloadLinkService
{
    @Autowired
    private DownloadLinkMapper downloadLinkMapper;

    /**
     * 查询下载链接
     *
     * @param id 下载链接主键
     * @return 下载链接
     */
    @Override
    public DownloadLink selectDownloadLinkById(Long id)
    {
        return downloadLinkMapper.selectDownloadLinkById(id);
    }

    /**
     * 查询下载链接列表
     *
     * @param downloadLink 下载链接
     * @return 下载链接集合
     */
    @Override
    public List<DownloadLink> selectDownloadLinkList(DownloadLink downloadLink)
    {
        return downloadLinkMapper.selectDownloadLinkList(downloadLink);
    }

    /**
     * 新增下载链接
     *
     * @param downloadLink 下载链接
     * @return 结果
     */
    @Override
    public int insertDownloadLink(DownloadLink downloadLink)
    {
        return downloadLinkMapper.insertDownloadLink(downloadLink);
    }

    /**
     * 修改下载链接
     *
     * @param downloadLink 下载链接
     * @return 结果
     */
    @Override
    public int updateDownloadLink(DownloadLink downloadLink)
    {
        return downloadLinkMapper.updateDownloadLink(downloadLink);
    }

    /**
     * 批量删除下载链接
     *
     * @param ids 需要删除的下载链接主键集合
     * @return 结果
     */
    @Override
    public int deleteDownloadLinkByIds(Long[] ids)
    {
        return downloadLinkMapper.deleteDownloadLinkByIds(ids);
    }

    /**
     * 删除下载链接信息
     *
     * @param id 下载链接主键
     * @return 结果
     */
    @Override
    public int deleteDownloadLinkById(Long id)
    {
        return downloadLinkMapper.deleteDownloadLinkById(id);
    }
}
