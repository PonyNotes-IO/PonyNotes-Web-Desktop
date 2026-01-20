package com.ruoyi.xmbj.mapper;

import java.util.List;
import com.ruoyi.xmbj.domain.AfUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-12-20
 */
public interface AfUserMapper extends BaseMapper<AfUser> {

    /**
     * 查询用户信息列表
     *
     * @param afUser 用户信息
     * @return 用户信息集合
     */
    public List<AfUser> selectAfUserList(AfUser afUser);

}