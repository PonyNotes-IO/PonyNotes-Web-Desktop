package com.ruoyi.xmbj.mapper;

import com.ruoyi.xmbj.domain.UserUsage;

public interface UserUsageMapper {
    UserUsage selectUserUsage(Long userId);

    void insert(UserUsage newUsage);

    void initUserUsage(Long userId);

    void addStorageUsage(Long userId, Long quantity);

    void addAiCalls(Long userId, int intValue);
}
