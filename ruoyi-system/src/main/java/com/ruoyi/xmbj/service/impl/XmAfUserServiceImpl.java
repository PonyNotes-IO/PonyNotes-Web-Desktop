package com.ruoyi.xmbj.service.impl;

import java.util.List;

import com.ruoyi.xmbj.domain.XmAfUser;
import com.ruoyi.xmbj.mapper.XmAfUserMapper;
import com.ruoyi.xmbj.service.IXmAfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;

/**
 * 用户管理11Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class XmAfUserServiceImpl implements IXmAfUserService
{
    @Autowired
    private XmAfUserMapper afUserMapper;

    /**
     * 查询用户管理11
     * 
     * @param uid 用户管理11主键
     * @return 用户管理11
     */
    @Override
    public XmAfUser selectAfUserByUid(Long uid)
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
    public List<XmAfUser> selectAfUserList(XmAfUser afUser)
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
    public int insertAfUser(XmAfUser afUser)
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
    public int updateAfUser(XmAfUser afUser)
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
