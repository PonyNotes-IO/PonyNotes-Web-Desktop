package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfWorkspaceInvitation;
import org.apache.ibatis.annotations.Mapper;

/**
 * AfWorkspaceInvitation数据访问接口
 */
@Mapper
@DataSource(DataSourceType.SLAVE)
public interface AfWorkspaceInvitationMapper extends BaseMapper<AfWorkspaceInvitation> {
}