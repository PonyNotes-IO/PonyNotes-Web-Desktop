package com.ruoyi.xmbj.mapper;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.UserUsage;

@DataSource(DataSourceType.SLAVE)
public interface UserUsageMapper {
    UserUsage selectUserUsage(Long userId);

    void initUserUsage(Long userId);

    void addStorageUsage(Long userId, Long quantity);

    void addAiCalls(Long userId, int intValue);
}
