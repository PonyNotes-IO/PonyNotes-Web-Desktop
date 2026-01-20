package com.ruoyi.xmbj.service;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfWorkspace;
import com.ruoyi.xmbj.domain.ClientUser;
import com.ruoyi.xmbj.mapper.AfWorkspaceMapper;
import com.ruoyi.xmbj.mapper.ClientUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ClientUserService {
    @Autowired
    private ClientUserMapper clientUserMapper;

    @Autowired
    private AfWorkspaceMapper afWorkspaceMapper;

    /**
     * 从 PostgreSQL 从库查询客户端用户信息
     * 使用 @DataSource(DataSourceType.slave) 注解确保此方法使用从库数据源
     */
    @DataSource(DataSourceType.slave)
    public ClientUser getClientUserByUserInfo(String userInfo) {
        return clientUserMapper.getClientUserByUserInfo(userInfo);
    }

    @DataSource(DataSourceType.slave)
    public List<ClientUser> selectAfUserList(ClientUser clientUser) {
        return clientUserMapper.selectAfUserList(clientUser);
    }

    @DataSource(DataSourceType.slave)
    public ClientUser selectAfUserById(Long afuserId) {
        return clientUserMapper.selectAfUserById(afuserId);
    }

    @DataSource(DataSourceType.slave)
    public int updateAfUser(ClientUser clientUser) {
        return clientUserMapper.updateAfUser(clientUser);
    }

    @DataSource(DataSourceType.slave)
    public int remove(Long[] afuserIds) {
        return clientUserMapper.remove(afuserIds);
    }

    @DataSource(DataSourceType.slave)
    public int changestatus(Long[] afuserIds) {
        return clientUserMapper.changestatus(afuserIds);
    }

    @DataSource(DataSourceType.slave)
    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace) {
        return afWorkspaceMapper.selectAfWorkspaceList(afWorkspace);
    }

}
