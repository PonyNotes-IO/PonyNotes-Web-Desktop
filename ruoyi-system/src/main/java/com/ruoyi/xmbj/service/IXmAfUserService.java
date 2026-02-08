package com.ruoyi.xmbj.service;

import java.util.List;
import com.ruoyi.xmbj.domain.XmAfUser;
import com.ruoyi.xmbj.domain.dto.XmAfUserDTO;

/**
 * 用户管理11Service接口
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
public interface IXmAfUserService
{
    /**
     * 查询用户管理11
     * 
     * @param uid 用户管理11主键
     * @return 用户管理11
     */
    public XmAfUser selectAfUserByUid(Long uid);

    /**
     * 查询用户管理11列表
     * 
     * @param afUser 用户管理11
     * @return 用户管理11集合
     */
    public List<XmAfUser> selectAfUserList(XmAfUser afUser);

    /**
     * 新增用户管理11
     * 
     * @param afUser 用户管理11
     * @return 结果
     */
    public int insertAfUser(XmAfUser afUser);

    /**
     * 修改用户管理11
     * 
     * @param afUser 用户管理11
     * @return 结果
     */
    public int updateAfUser(XmAfUser afUser);

    /**
     * 批量删除用户管理11
     * 
     * @param uids 需要删除的用户管理11主键集合
     * @return 结果
     */
    public int deleteAfUserByUids(Long[] uids);

    /**
     * 删除用户管理11信息
     * 
     * @param uid 用户管理11主键
     * @return 结果
     */
    public int deleteAfUserByUid(Long uid);

    List<XmAfUserDTO> selectAfUserListDTO(XmAfUser afUser);
}
