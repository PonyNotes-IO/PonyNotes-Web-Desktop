package com.ruoyi.xmbj.mapper;

import com.ruoyi.xmbj.domain.AfUserWorkspace;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.NoteBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工作区Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface AfWorkspaceMapper {
    /**
     * 查询工作区
     *
     * @param workspaceId 工作区主键
     * @return 工作区
     */
    public AfWorkspace selectAfWorkspaceByWorkspaceId(String workspaceId);

    /**
     * 查询工作区列表
     *
     * @param afWorkspace 工作区
     * @return 工作区集合
     */
    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace);

    /**
     * 新增工作区
     *
     * @param afWorkspace 工作区
     * @return 结果
     */
    public int insertAfWorkspace(AfWorkspace afWorkspace);

    /**
     * 修改工作区
     *
     * @param afWorkspace 工作区
     * @return 结果
     */
    public int updateAfWorkspace(AfWorkspace afWorkspace);

    /**
     * 删除工作区
     *
     * @param workspaceId 工作区主键
     * @return 结果
     */
    public int deleteAfWorkspaceByWorkspaceId(String workspaceId);

    /**
     * 批量删除工作区
     *
     * @param workspaceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAfWorkspaceByWorkspaceIds(String[] workspaceIds);

    /**
     * 用户工作空间列表
     * @param afUserWorkspace
     * @return
     */
    List<AfUserWorkspace> selectAfUserWorkspaceList(AfUserWorkspace afUserWorkspace);

}