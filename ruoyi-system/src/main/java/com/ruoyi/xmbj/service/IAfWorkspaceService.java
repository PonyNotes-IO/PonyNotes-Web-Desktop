package com.ruoyi.xmbj.service;

import java.util.List;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.vo.AfWorkspaceVo;

public interface IAfWorkspaceService 
{
    public AfWorkspace selectAfWorkspaceByWorkspaceId(String workspaceId);

    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace);

    public List<AfWorkspaceVo> selectAfWorkspaceVoList(AfWorkspace afWorkspace);

    public int insertAfWorkspace(AfWorkspace afWorkspace);

    public int updateAfWorkspace(AfWorkspace afWorkspace);

    public int deleteAfWorkspaceByWorkspaceIds(String[] workspaceIds);

    public int deleteAfWorkspaceByWorkspaceId(String workspaceId);
}
