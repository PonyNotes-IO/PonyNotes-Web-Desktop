package com.ruoyi.xmbj.service;

import java.util.List;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;

public interface IAfSubscriptionAddonsService 
{
    public AfSubscriptionAddons selectAfSubscriptionAddonsById(Long id);

    public AfSubscriptionAddons selectAfSubscriptionAddonsByAddonCode(String addonCode);

    public List<AfSubscriptionAddons> selectAfSubscriptionAddonsList(AfSubscriptionAddons afSubscriptionAddons);

    public int insertAfSubscriptionAddons(AfSubscriptionAddons afSubscriptionAddons);

    public int updateAfSubscriptionAddons(AfSubscriptionAddons afSubscriptionAddons);

    public int deleteAfSubscriptionAddonsByIds(Long[] ids);

    public int deleteAfSubscriptionAddonsById(Long id);
}
