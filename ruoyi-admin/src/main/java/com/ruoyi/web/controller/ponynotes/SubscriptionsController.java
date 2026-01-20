package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xmbj.domain.AfSubscriptionPlans;
import com.ruoyi.xmbj.domain.AfUserSubscriptions;
import com.ruoyi.xmbj.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ponynotes/subscriptions")
public class SubscriptionsController extends BaseController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PreAuthorize("@ss.hasPermi('ponynotes:appversion:mySubscriptions')")
    @GetMapping("/my")
    public TableDataInfo mySubscriptions(@RequestParam Long userId) {
        startPage();
        List<AfUserSubscriptions> list =  subscriptionService.getSubscriptionHistoryPaged(userId);
        return getDataTable(list);
    }

    @PostMapping("/subscribe")
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

    /**
     * 套餐详情
     */
    @PostMapping("/detail")
    public AjaxResult detail(@RequestParam  List<Long> planIds) {
        List<AfSubscriptionPlans> list = new ArrayList<>();
        for (Long planId:planIds) {
            AfSubscriptionPlans afSubscriptionPlans =  subscriptionService.getSubscriptionPlanById(planId);
            list.add(afSubscriptionPlans);
        }
        return AjaxResult.success(list);
    }

    /**
     * 套餐配置-年卡月卡
     */
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody List<AfSubscriptionPlans> subscriptionPlanList) {
        for (AfSubscriptionPlans item:subscriptionPlanList) {
            int result  = subscriptionService.updateAfSubscriptionPlans(item);
        }
        return AjaxResult.success("修改套餐成功");
    }



}
