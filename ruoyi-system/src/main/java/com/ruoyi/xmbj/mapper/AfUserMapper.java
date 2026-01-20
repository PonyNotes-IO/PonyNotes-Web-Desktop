package com.ruoyi.xmbj.mapper;

import java.util.List;
import com.ruoyi.xmbj.domain.AfUser;

/**
 * 用户管理11Mapper接口
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
public interface AfUserMapper 
{
    /**
     * 查询用户管理11
     * 
     * @param uid 用户管理11主键
     * @return 用户管理11
     */
    public AfUser selectAfUserByUid(Long uid);

    /**
     * 查询用户管理11列表
     * 
     * @param afUser 用户管理11
     * @return 用户管理11集合
     */
    public List<AfUser> selectAfUserList(AfUser afUser);

    /**
     * 新增用户管理11
     * 
     * @param afUser 用户管理11
     * @return 结果
     */
    public int insertAfUser(AfUser afUser);

    /**
     * 修改用户管理11
     * 
     * @param afUser 用户管理11
     * @return 结果
     */
    public int updateAfUser(AfUser afUser);

    /**
     * 删除用户管理11
     * 
     * @param uid 用户管理11主键
     * @return 结果
     */
    public int deleteAfUserByUid(Long uid);

    /**
     * 批量删除用户管理11
     * 
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAfUserByUids(Long[] uids);
}
