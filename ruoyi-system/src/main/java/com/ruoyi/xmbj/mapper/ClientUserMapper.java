package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.Addon;
import com.ruoyi.xmbj.domain.ClientUser;

import java.util.List;

public interface ClientUserMapper extends BaseMapper<ClientUser> {
    ClientUser getClientUserByUserInfo(String userInfo);

    List<ClientUser> selectAfUserList(ClientUser clientUser);

    ClientUser selectAfUserById(Long afuserId);

    int updateAfUser(ClientUser clientUser);

    int remove(Long[] afuserIds);

    int changestatus(Long[] afuserIds);
}
