package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AfUser;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface AfUserMapper
{
    /**
     * 查询用户信息
     * 
     * @param user 用户信息
     * @return 用户信息
     */
    public AfUser selectUser(AfUser user);

    /**
     * 通过ID查询用户
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
     * 根据UUID查询用户
     * 
     * @param uuid 用户UUID
     * @return 用户信息
     */
    public AfUser checkUuidUnique(String uuid);

    /**
     * 根据邮箱查询用户
     * 
     * @param email 用户邮箱
     * @return 用户信息
     */
    public AfUser checkEmailUnique(String email);

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
     * 删除用户
     * 
     * @param uid 用户ID
     * @return 结果
     */
    public int deleteUserById(Long uid);

    /**
     * 批量删除用户
     * 
     * @param uids 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] uids);
}