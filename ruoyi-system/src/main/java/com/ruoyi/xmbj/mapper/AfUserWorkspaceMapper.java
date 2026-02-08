package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfUserWorkspace;
import org.apache.ibatis.annotations.Mapper;

/**
 * AfUserWorkspace数据访问接口
 */
@Mapper
@DataSource(DataSourceType.SLAVE)
public interface AfUserWorkspaceMapper extends BaseMapper<AfUserWorkspace> {
}