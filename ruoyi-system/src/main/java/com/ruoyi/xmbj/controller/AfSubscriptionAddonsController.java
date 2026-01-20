package com.ruoyi.xmbj.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xmbj.domain.AfSubscriptionAddons;
import com.ruoyi.xmbj.service.IAfSubscriptionAddonsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/xmbj/subscriptionAddons")
public class AfSubscriptionAddonsController extends BaseController
{
    @Autowired
    private IAfSubscriptionAddonsService afSubscriptionAddonsService;

    @PreAuthorize("@ss.hasPermi('xmbj:subscriptionAddons:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfSubscriptionAddons afSubscriptionAddons)
    {
        startPage();
        List<AfSubscriptionAddons> list = afSubscriptionAddonsService.selectAfSubscriptionAddonsList(afSubscriptionAddons);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('xmbj:subscriptionAddons:export')")
    @Log(title = "订阅补充包", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfSubscriptionAddons afSubscriptionAddons)
    {
        List<AfSubscriptionAddons> list = afSubscriptionAddonsService.selectAfSubscriptionAddonsList(afSubscriptionAddons);
        ExcelUtil<AfSubscriptionAddons> util = new ExcelUtil<AfSubscriptionAddons>(AfSubscriptionAddons.class);
        util.exportExcel(response, list, "订阅补充包数据");
    }

    @PreAuthorize("@ss.hasPermi('xmbj:subscriptionAddons:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(afSubscriptionAddonsService.selectAfSubscriptionAddonsById(id));
    }

    @PreAuthorize("@ss.hasPermi('xmbj:subscriptionAddons:add')")
    @Log(title = "订阅补充包", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfSubscriptionAddons afSubscriptionAddons)
    {
        return toAjax(afSubscriptionAddonsService.insertAfSubscriptionAddons(afSubscriptionAddons));
    }

    @PreAuthorize("@ss.hasPermi('xmbj:subscriptionAddons:edit')")
    @Log(title = "订阅补充包", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfSubscriptionAddons afSubscriptionAddons)
    {
        return toAjax(afSubscriptionAddonsService.updateAfSubscriptionAddons(afSubscriptionAddons));
    }

    @PreAuthorize("@ss.hasPermi('xmbj:subscriptionAddons:remove')")
    @Log(title = "订阅补充包", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(afSubscriptionAddonsService.deleteAfSubscriptionAddonsByIds(ids));
    }
}
