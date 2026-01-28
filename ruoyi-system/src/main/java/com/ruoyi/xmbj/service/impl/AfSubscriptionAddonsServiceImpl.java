package com.ruoyi.xmbj.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.mapper.AfSubscriptionAddonsMapper;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.service.IAfSubscriptionAddonsService;

@Service
@DataSource(DataSourceType.SLAVE)
public class AfSubscriptionAddonsServiceImpl implements IAfSubscriptionAddonsService 
{
    @Autowired
    private AfSubscriptionAddonsMapper afSubscriptionAddonsMapper;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionAddons selectAfSubscriptionAddonsById(Long id)
    {
        return afSubscriptionAddonsMapper.selectAfSubscriptionAddonsById(id);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionAddons selectAfSubscriptionAddonsByAddonCode(String addonCode)
    {
        return afSubscriptionAddonsMapper.selectAfSubscriptionAddonsByAddonCode(addonCode);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionAddons> selectAfSubscriptionAddonsList(AfSubscriptionAddons afSubscriptionAddons)
    {
        return afSubscriptionAddonsMapper.selectAfSubscriptionAddonsList(afSubscriptionAddons);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int insertAfSubscriptionAddons(AfSubscriptionAddons afSubscriptionAddons)
    {
        return afSubscriptionAddonsMapper.insertAfSubscriptionAddons(afSubscriptionAddons);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int updateAfSubscriptionAddons(AfSubscriptionAddons afSubscriptionAddons)
    {
        return afSubscriptionAddonsMapper.updateAfSubscriptionAddons(afSubscriptionAddons);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfSubscriptionAddonsByIds(Long[] ids)
    {
        return afSubscriptionAddonsMapper.deleteAfSubscriptionAddonsByIds(ids);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public int deleteAfSubscriptionAddonsById(Long id)
    {
        return afSubscriptionAddonsMapper.deleteAfSubscriptionAddonsById(id);
    }
}
