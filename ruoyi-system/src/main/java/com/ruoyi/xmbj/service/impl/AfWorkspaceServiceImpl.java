package com.ruoyi.xmbj.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.mapper.AfWorkspaceMapper;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.vo.AfWorkspaceVo;
import com.ruoyi.xmbj.service.IAfWorkspaceService;

@Service
@DataSource(DataSourceType.SLAVE)
public class AfWorkspaceServiceImpl implements IAfWorkspaceService 
{
    @Autowired
    private AfWorkspaceMapper afWorkspaceMapper;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public AfWorkspace selectAfWorkspaceByWorkspaceId(String workspaceId)
    {
        return afWorkspaceMapper.selectAfWorkspaceByWorkspaceId(workspaceId);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace)
    {
        return afWorkspaceMapper.selectAfWorkspaceList(afWorkspace);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<AfWorkspaceVo> selectAfWorkspaceVoList(AfWorkspace afWorkspace)
    {
        return afWorkspaceMapper.selectAfWorkspaceVoList(afWorkspace);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int insertAfWorkspace(AfWorkspace afWorkspace)
    {
        return afWorkspaceMapper.insertAfWorkspace(afWorkspace);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int updateAfWorkspace(AfWorkspace afWorkspace)
    {
        return afWorkspaceMapper.updateAfWorkspace(afWorkspace);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfWorkspaceByWorkspaceIds(String[] workspaceIds)
    {
        return afWorkspaceMapper.deleteAfWorkspaceByWorkspaceIds(workspaceIds);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfWorkspaceByWorkspaceId(String workspaceId)
    {
        return afWorkspaceMapper.deleteAfWorkspaceByWorkspaceId(workspaceId);
    }
}
