package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AfUser;

/**
 * 用户表 服务层
 * 
 * @author ruoyi
 */
public interface IAfUserService
{
    /**
     * 查询用户信息
     * 
     * @param uid 用户ID
     * @return 用户信息
     */
    public AfUser selectUserById(Long uid);

    /**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
    public List<AfUser> selectUserList(AfUser user);

    /**
     * 新增用户
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(AfUser user);

    /**
     * 修改用户
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(AfUser user);

    /**
     * 删除用户信息
     * 
     * @param uid 用户ID
     * @return 结果
     */
    public int deleteUserById(Long uid);

    /**
     * 批量删除用户信息
     * 
     * @param uids 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] uids);

    /**
     * 校验UUID是否唯一
     *
     * @param uuid 用户UUID
     * @return 结果
     */
    public boolean checkUuidUnique(String uuid);

    /**
     * 校验邮箱是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean checkEmailUnique(AfUser user);
}