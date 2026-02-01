package com.ruoyi.xmbj.mapper;

import java.util.List;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;

public interface AfSubscriptionAddonsMapper 
{
    public AfSubscriptionAddons selectAfSubscriptionAddonsById(Long id);

    public AfSubscriptionAddons selectAfSubscriptionAddonsByAddonCode(String addonCode);

    public List<AfSubscriptionAddons> selectAfSubscriptionAddonsList(AfSubscriptionAddons afSubscriptionAddons);

    public int insertAfSubscriptionAddons(AfSubscriptionAddons afSubscriptionAddons);

    public int updateAfSubscriptionAddons(AfSubscriptionAddons afSubscriptionAddons);

    public int deleteAfSubscriptionAddonsById(Long id);

    public int deleteAfSubscriptionAddonsByIds(Long[] ids);

    AfSubscriptionAddons selectById(Long addonId);

    List<AfSubscriptionAddons> selectByIds(String addonId);

    int deleteById(Long id);

    int insert(AfSubscriptionAddons addon);

    List<AfSubscriptionAddons> selectAddonsByType(String type);

    List<AfSubscriptionAddons> selectActiveAddons();
}
