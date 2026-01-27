package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xmbj.domain.*;
import com.ruoyi.xmbj.service.SubscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "小马笔记",tags = {"小马笔记"})
@RequestMapping("/api/ponynotes")
public class PonynotesController extends BaseController {

    @Autowired
    private SubscriptionService subscriptionService;

//    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:listPlans')")
    @GetMapping("/listPlans")
    @ApiOperation("查询计划")
    public TableDataInfo listPlans() {
        startPage();
        List<AfSubscriptionPlans> list=  subscriptionService.getSubscriptionPlansPaged();
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:getPlan')")
    @GetMapping("/plans/{id}")
    public AjaxResult getPlan(@PathVariable Long id) {
        AfSubscriptionPlans plan = subscriptionService.getSubscriptionPlanById(id);
        return AjaxResult.success(plan);
    }

    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:subscriptions')")
    @ApiOperation("我的订阅")
    @GetMapping("/my/subscriptions")
    public TableDataInfo mySubscriptions(@RequestParam Long userId) {
        startPage();
        List<AfUserSubscriptions> list =  subscriptionService.getSubscriptionHistoryPaged(userId);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:subscribe')")
    @PostMapping("/subscribe")
    @ApiOperation("订阅")
    public AjaxResult subscribe(@RequestParam Long userId,
            @RequestParam Long planId,
            @RequestParam String billingType) {
        AfUserSubscriptions subscription = subscriptionService.subscribe(userId, planId, billingType);
        return AjaxResult.success(subscription);
    }

    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestParam Long userId) {
        subscriptionService.cancelSubscription(userId);
        return AjaxResult.success();
    }
}
