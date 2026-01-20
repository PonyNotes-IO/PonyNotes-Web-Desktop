package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysWorkspace;

/**
 * 工作空间Mapper接口
 * 
 * @author ruoyi
 */
public interface SysWorkspaceMapper
{
    /**
     * 查询工作空间
     * 
     * @param workspaceId 工作空间ID
     * @return 工作空间
     */
    public SysWorkspace selectWorkspaceById(String workspaceId);

    /**
     * 查询工作空间列表
     * 
     * @param sysWorkspace 工作空间
     * @return 工作空间集合
     */
    public List<SysWorkspace> selectWorkspaceList(SysWorkspace sysWorkspace);

    /**
     * 新增工作空间
     * 
     * @param sysWorkspace 工作空间
     * @return 结果
     */
    public int insertWorkspace(SysWorkspace sysWorkspace);

    /**
     * 修改工作空间
     * 
     * @param sysWorkspace 工作空间
     * @return 结果
     */
    public int updateWorkspace(SysWorkspace sysWorkspace);

    /**
     * 删除工作空间
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