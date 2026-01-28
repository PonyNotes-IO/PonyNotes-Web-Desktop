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
     * 使用 @DataSource(DataSourceType.SLAVE) 注解确保此方法使用从库数据源
     */
    @DataSource(DataSourceType.SLAVE)
    public ClientUser getClientUserByUserInfo(String userInfo) {
        return clientUserMapper.getClientUserByUserInfo(userInfo);
    }
    @DataSource(DataSourceType.SLAVE)
    public ClientUser getClientUserByUuid(String userInfo) {
        return clientUserMapper.getClientUserByUuid(userInfo);
    }

    @DataSource(DataSourceType.SLAVE)
    public List<ClientUser> selectAfUserList(ClientUser clientUser) {
        return clientUserMapper.selectAfUserList(clientUser);
    }

    @DataSource(DataSourceType.SLAVE)
    public ClientUser selectAfUserById(Long afuserId) {
        return clientUserMapper.selectAfUserById(afuserId);
    }

    @DataSource(DataSourceType.SLAVE)
    public int updateAfUser(ClientUser clientUser) {
        return clientUserMapper.updateAfUser(clientUser);
    }

    @DataSource(DataSourceType.SLAVE)
    public int remove(Long[] afuserIds) {
        return clientUserMapper.remove(afuserIds);
    }

    @DataSource(DataSourceType.SLAVE)
    public int changestatus(Long[] afuserIds) {
        return clientUserMapper.changestatus(afuserIds);
    }

    @DataSource(DataSourceType.SLAVE)
    public List<AfWorkspace> selectAfWorkspaceList(AfWorkspace afWorkspace) {
        return afWorkspaceMapper.selectAfWorkspaceList(afWorkspace);
    }

}
