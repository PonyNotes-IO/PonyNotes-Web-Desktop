package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.AfUser;
import com.ruoyi.system.mapper.AfUserMapper;
import com.ruoyi.system.service.IAfUserService;

/**
 * 用户表 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class AfUserServiceImpl implements IAfUserService
{
    @Autowired
    private AfUserMapper userMapper;

    /**
     * 查询用户信息
     * 
     * @param uid 用户ID
     * @return 用户信息
     */
    @Override
    public AfUser selectUserById(Long uid)
    {
        return userMapper.selectUserById(uid);
    }

    /**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
    @Override
    public List<AfUser> selectUserList(AfUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增用户
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(AfUser user)
    {
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(AfUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 删除用户信息
     * 
     * @param uid 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long uid)
    {
        return userMapper.deleteUserById(uid);
    }

    /**
     * 批量删除用户信息
     * 
     * @param uids 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] uids)
    {
        return userMapper.deleteUserByIds(uids);
    }

    /**
     * 校验UUID是否唯一
     *
     * @param uuid 用户UUID
     * @return 结果
     */
    @Override
    public boolean checkUuidUnique(String uuid)
    {
        AfUser user = userMapper.checkUuidUnique(uuid);
        if (StringUtils.isNotNull(user))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验邮箱是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkEmailUnique(AfUser user)
    {
        Long uid = StringUtils.isNull(user.getUid()) ? -1L : user.getUid();
        AfUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUid().longValue() != uid.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}