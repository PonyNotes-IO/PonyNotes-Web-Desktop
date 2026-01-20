package com.ruoyi.xmbj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xmbj.domain.AfUserAddons;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AfUserAddons数据访问接口
 */
@Mapper
public interface AfUserAddonsMapper extends BaseMapper<AfUserAddons> {
    void updateExpiredAddons(Long userId);

    List<AfUserAddons> selectActiveUserAddons(Long userId);

    List<AfUserAddons> selectUserAddonsByStatus(Long userId, String status);

    AfUserAddons selectUserAddon(Long userId, Long addonId);

    AfUserAddons selectUserAddonById(Long valueOf);
}