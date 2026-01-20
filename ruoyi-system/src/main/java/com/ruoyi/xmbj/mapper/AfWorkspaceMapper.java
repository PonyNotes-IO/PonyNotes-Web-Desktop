package com.ruoyi.xmbj.mapper;

import java.util.List;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.vo.AfWorkspaceVo;

public interface AfWorkspaceMapper 
{
    public AfWorkspace selectAfWorkspaceByWorkspaceId(String workspaceId);

    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace);

    public List<AfWorkspaceVo> selectAfWorkspaceVoList(AfWorkspace afWorkspace);

    public int insertAfWorkspace(AfWorkspace afWorkspace);

    public int updateAfWorkspace(AfWorkspace afWorkspace);

    public int deleteAfWorkspaceByWorkspaceId(String workspaceId);

    public int deleteAfWorkspaceByWorkspaceIds(String[] workspaceIds);
}
