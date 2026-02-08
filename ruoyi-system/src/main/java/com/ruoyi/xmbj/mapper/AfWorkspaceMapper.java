package com.ruoyi.xmbj.mapper;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfUserWorkspace;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.vo.AfWorkspaceVo;

@DataSource(DataSourceType.SLAVE)
public interface AfWorkspaceMapper 
{
    public AfWorkspace selectAfWorkspaceByWorkspaceId(String workspaceId);

    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace);

    public List<AfWorkspaceVo> selectAfWorkspaceVoList(AfWorkspace afWorkspace);

    public int insertAfWorkspace(AfWorkspace afWorkspace);

    public int updateAfWorkspace(AfWorkspace afWorkspace);

    public int deleteAfWorkspaceByWorkspaceId(String workspaceId);

    public int deleteAfWorkspaceByWorkspaceIds(String[] workspaceIds);

    List<AfUserWorkspace> selectAfUserWorkspaceList(AfUserWorkspace afUserWorkspace);
}