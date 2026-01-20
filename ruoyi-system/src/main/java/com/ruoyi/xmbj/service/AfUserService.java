package com.ruoyi.xmbj.service;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xmbj.mapper.AfUserMapper;
import com.ruoyi.xmbj.domain.AfUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 用户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-12-20
 */
@Slf4j
@Service
public class AfUserService {

    @Autowired
    private AfUserMapper afUserMapper;

    /**
     * 查询用户信息列表
     *
     * @param afUser 用户信息
     * @return 用户信息
     */
    @DataSource(DataSourceType.slave)
    public List<AfUser> selectAfUserList(AfUser afUser) {
        return afUserMapper.selectAfUserList(afUser);
    }

}