package com.ruoyi.xmbj.mapper;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.XmAfUser;
import com.ruoyi.xmbj.domain.dto.XmAfUserDTO;

/**
 * 用户管理11Mapper接口
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
@DataSource(DataSourceType.SLAVE)
public interface XmAfUserMapper
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

    List<XmAfUserDTO> selectAfUserListDTO(XmAfUser afUser);
}