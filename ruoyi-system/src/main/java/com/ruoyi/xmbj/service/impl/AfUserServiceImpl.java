package com.ruoyi.xmbj.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.mapper.AfUserMapper;
import com.ruoyi.xmbj.domain.AfUser;
import com.ruoyi.xmbj.service.IAfUserService;

/**
 * 用户管理11Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class AfUserServiceImpl implements IAfUserService 
{
    @Autowired
    private AfUserMapper afUserMapper;

    /**
     * 查询用户管理11
     * 
     * @param uid 用户管理11主键
     * @return 用户管理11
     */
    @Override
    public AfUser selectAfUserByUid(Long uid)
    {
        return afUserMapper.selectAfUserByUid(uid);
    }

    /**
     * 查询用户管理11列表
     * 
     * @param afUser 用户管理11
     * @return 用户管理11
     */
    @Override
    public List<AfUser> selectAfUserList(AfUser afUser)
    {
        return afUserMapper.selectAfUserList(afUser);
    }

    /**
     * 新增用户管理11
     * 
     * @param afUser 用户管理11
     * @return 结果
     */
    @Override
    public int insertAfUser(AfUser afUser)
    {
        return afUserMapper.insertAfUser(afUser);
    }

    /**
     * 修改用户管理11
     * 
     * @param afUser 用户管理11
     * @return 结果
     */
    @Override
    public int updateAfUser(AfUser afUser)
    {
        return afUserMapper.updateAfUser(afUser);
    }

    /**
     * 批量删除用户管理11
     * 
     * @param uids 需要删除的用户管理11主键
     * @return 结果
     */
    @Override
    public int deleteAfUserByUids(Long[] uids)
    {
        return afUserMapper.deleteAfUserByUids(uids);
    }

    /**
     * 删除用户管理11信息
     * 
     * @param uid 用户管理11主键
     * @return 结果
     */
    @Override
    public int deleteAfUserByUid(Long uid)
    {
        return afUserMapper.deleteAfUserByUid(uid);
    }
}
