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
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.service.IAfSubscriptionPlansService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订阅套餐Controller
 * 
 * @author 张继科
 * @date 2026-01-21 xmbj/plans/index
 */
@RestController
@RequestMapping("/xmbj/plans")
public class AfSubscriptionPlansController extends BaseController
{
    @Autowired
    private IAfSubscriptionPlansService afSubscriptionPlansService;

    /**
     * 测试接口（不需要认证）
     */
    @Anonymous
    @GetMapping("/test")
    public AjaxResult test()
    {
        try
        {
            Class<?> clazz = Class.forName("com.ruoyi.framework.datasource.DynamicDataSourceContextHolder");
            java.lang.reflect.Method setMethod = clazz.getMethod("setDataSourceType", String.class);
            setMethod.invoke(null, "SLAVE");
            System.out.println("AfSubscriptionPlansController.test: 手动切换到 SLAVE 数据源");
        }
        catch (Exception e)
        {
            System.out.println("AfSubscriptionPlansController.test: 切换数据源失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        List<AfSubscriptionPlans> list = afSubscriptionPlansService.selectAfSubscriptionPlansList(new AfSubscriptionPlans());
        return success(list);
    }

    /**
     * 查询订阅套餐列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:plans:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfSubscriptionPlans afSubscriptionPlans)
    {
        startPage();
        List<AfSubscriptionPlans> list = afSubscriptionPlansService.selectAfSubscriptionPlansList(afSubscriptionPlans);
        return getDataTable(list);
    }

    /**
     * 导出订阅套餐列表
     */
    @PreAuthorize("@ss.hasPermi('xmbj:plans:export')")
    @Log(title = "订阅套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfSubscriptionPlans afSubscriptionPlans)
    {
        List<AfSubscriptionPlans> list = afSubscriptionPlansService.selectAfSubscriptionPlansList(afSubscriptionPlans);
        ExcelUtil<AfSubscriptionPlans> util = new ExcelUtil<AfSubscriptionPlans>(AfSubscriptionPlans.class);
        util.exportExcel(response, list, "订阅套餐数据");
    }

    /**
     * 获取订阅套餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('xmbj:plans:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(afSubscriptionPlansService.selectAfSubscriptionPlansById(id));
    }

    /**
     * 新增订阅套餐
     */
    @PreAuthorize("@ss.hasPermi('xmbj:plans:add')")
    @Log(title = "订阅套餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfSubscriptionPlans afSubscriptionPlans)
    {
        return toAjax(afSubscriptionPlansService.insertAfSubscriptionPlans(afSubscriptionPlans));
    }

    /**
     * 修改订阅套餐
     */
    @PreAuthorize("@ss.hasPermi('xmbj:plans:edit')")
    @Log(title = "订阅套餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfSubscriptionPlans afSubscriptionPlans)
    {
        System.out.println("AfSubscriptionPlansController.edit: 接收到的数据:");
        System.out.println("  id: " + afSubscriptionPlans.getId());
        System.out.println("  hasInbox: " + afSubscriptionPlans.getHasInbox());
        System.out.println("  hasMultiDeviceSync: " + afSubscriptionPlans.getHasMultiDeviceSync());
        System.out.println("  hasApiSupport: " + afSubscriptionPlans.getHasApiSupport());
        System.out.println("  hasShareLink: " + afSubscriptionPlans.getHasShareLink());
        System.out.println("  hasPublish: " + afSubscriptionPlans.getHasPublish());
        System.out.println("  hasSpaceMemberManagement: " + afSubscriptionPlans.getHasSpaceMemberManagement());
        System.out.println("  hasSpaceMemberGrouping: " + afSubscriptionPlans.getHasSpaceMemberGrouping());
        System.out.println("  isActive: " + afSubscriptionPlans.getIsActive());
        return toAjax(afSubscriptionPlansService.updateAfSubscriptionPlans(afSubscriptionPlans));
    }

    /**
     * 删除订阅套餐
     */
    @PreAuthorize("@ss.hasPermi('xmbj:plans:remove')")
    @Log(title = "订阅套餐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids)
        {
            int count = afSubscriptionPlansService.countUserSubscriptionsByPlanId(id);
            if (count > 0)
            {
                return error("有购买记录，只能禁用");
            }
        }
        return toAjax(afSubscriptionPlansService.deleteAfSubscriptionPlansByIds(ids));
    }
}
