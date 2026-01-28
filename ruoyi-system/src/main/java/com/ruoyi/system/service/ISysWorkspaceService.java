package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysWorkspace;

/**
 * 工作空间 业务层
 * 
 * @author ruoyi
 */
public interface ISysWorkspaceService
{
    /**
     * 查询工作空间列表
     * 
     * @param sysWorkspace 工作空间信息
     * @return 工作空间集合
     */
    public List<SysWorkspace> selectWorkspaceList(SysWorkspace sysWorkspace);

    /**
     * 根据工作空间ID查询工作空间信息
     * 
     * @param workspaceId 工作空间ID
     * @return 工作空间信息
     */
    public SysWorkspace selectWorkspaceById(String workspaceId);

    /**
     * 新增工作空间
     * 
     * @param sysWorkspace 工作空间信息
     * @return 结果
     */
    public int insertWorkspace(SysWorkspace sysWorkspace);

    /**
     * 修改工作空间
     * 
     * @param sysWorkspace 工作空间信息
     * @return 结果
     */
    public int updateWorkspace(SysWorkspace sysWorkspace);

    /**
     * 根据工作空间ID删除工作空间
     * 
     * @param workspaceId 工作空间ID
     * @return 结果
     */
    public int deleteWorkspaceById(String workspaceId);

    /**
     * 批量删除工作空间
     * 
     * @param workspaceIds 需要删除的工作空间ID
     * @return 结果
     */
    public int deleteWorkspaceByIds(String[] workspaceIds);
}