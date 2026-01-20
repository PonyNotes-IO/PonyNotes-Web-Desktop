package com.ruoyi.web.controller.ponynotes;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xmbj.domain.UserUsage;
import com.ruoyi.xmbj.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ponynotes/usage")
public class UsageController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/my")
    public AjaxResult getUsage(@RequestParam Long userId) {
        UserUsage usage = subscriptionService.getUsage(userId);
        return AjaxResult.success(usage);
    }

    @PostMapping("/record")
    public AjaxResult record(@RequestParam Long userId,
            @RequestParam String usageType,
            @RequestParam Long quantity) {
        subscriptionService.recordUsage(userId, usageType, quantity);
        return AjaxResult.success();
    }
}
