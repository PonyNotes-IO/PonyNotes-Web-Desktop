package com.ruoyi.xmbj.service;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.XmAfUser;
import com.ruoyi.xmbj.mapper.XmAfUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-12-20
 */
@Slf4j
@Service
public class XmAfUserService {

    @Autowired
    private XmAfUserMapper afUserMapper;

    /**
     * 查询用户信息列表
     *
     * @param afUser 用户信息
     * @return 用户信息
     */
    @DataSource(DataSourceType.SLAVE)
    public List<XmAfUser> selectAfUserList(XmAfUser afUser) {
        return afUserMapper.selectAfUserList(afUser);
    }

}