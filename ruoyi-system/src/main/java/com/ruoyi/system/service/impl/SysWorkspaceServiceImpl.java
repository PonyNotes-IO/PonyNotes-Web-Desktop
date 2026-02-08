package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysWorkspaceMapper;
import com.ruoyi.system.domain.SysWorkspace;
import com.ruoyi.system.service.ISysWorkspaceService;
 

/**
 * 工作空间 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysWorkspaceServiceImpl implements ISysWorkspaceService
{
    @Autowired
    private SysWorkspaceMapper sysWorkspaceMapper;

    /**
     * 查询工作空间列表
     * 
     * @param sysWorkspace 工作空间信息
     * @return 工作空间集合
     */
    @Override
    public List<SysWorkspace> selectWorkspaceList(SysWorkspace sysWorkspace)
    {
        return sysWorkspaceMapper.selectWorkspaceList(sysWorkspace);
    }

    /**
     * 根据工作空间ID查询工作空间信息
     * 
     * @param workspaceId 工作空间ID
     * @return 工作空间信息
     */
    @Override
    public SysWorkspace selectWorkspaceById(String workspaceId)
    {
        return sysWorkspaceMapper.selectWorkspaceById(workspaceId);
    }

    /**
     * 新增工作空间
     * 
     * @param sysWorkspace 工作空间信息
     * @return 结果
     */
    @Override
    public int insertWorkspace(SysWorkspace sysWorkspace)
    {
        return sysWorkspaceMapper.insertWorkspace(sysWorkspace);
    }

    /**
     * 修改工作空间
     * 
     * @param sysWorkspace 工作空间信息
     * @return 结果
     */
    @Override
    public int updateWorkspace(SysWorkspace sysWorkspace)
    {
        return sysWorkspaceMapper.updateWorkspace(sysWorkspace);
    }

    /**
     * 根据工作空间ID删除工作空间
     * 
     * @param workspaceId 工作空间ID
     * @return 结果
     */
    @Override
    public int deleteWorkspaceById(String workspaceId)
    {
        return sysWorkspaceMapper.deleteWorkspaceById(workspaceId);
    }

    /**
     * 批量删除工作空间
     * 
     * @param workspaceIds 需要删除的工作空间ID
     * @return 结果
     */
    @Override
    public int deleteWorkspaceByIds(String[] workspaceIds)
    {
        return sysWorkspaceMapper.deleteWorkspaceByIds(workspaceIds);
    }
}