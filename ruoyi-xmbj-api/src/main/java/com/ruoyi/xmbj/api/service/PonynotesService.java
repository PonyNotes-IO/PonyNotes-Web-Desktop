package com.ruoyi.xmbj.api.service;

import com.ruoyi.xmbj.api.Urls;
import com.ruoyi.xmbj.api.protocol.ClientUser;
import com.ruoyi.xmbj.api.protocol.subscription.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * PonyNotes 订阅相关API FeignClient
 * 
 * 提供所有subscription（订阅管理）相关的API调用封装
 * 包括：订阅计划、补充包、订阅管理、使用情况等
 */
@FeignClient(name = "ponynotes-subscription-client", url = Urls.API_HOST, path = "/api/subscription")
public interface PonynotesService {

        /**
         * 获取订阅计划列表
         * 
         * @return 订阅计划响应
         */
        @GetMapping("/plans")
        GetSubscriptionPlansResponse getSubscriptionPlans();

        /**
         * 获取补充包列表
         * 
         * @return 补充包响应
         */
        @GetMapping("/addons")
        GetAddonsResponse getAddons();

        /**
         * 根据补充包类型获取补充包列表
         * 
         * @param addonType 补充包类型（storage/ai_quota/priority等）
         * @return 补充包响应
         */
        @GetMapping("/addons/by-type")
        GetAddonsResponse getAddonsByType(@RequestParam("addon_type") String addonType);

        /**
         * 创建/更新订阅
         * 需要用户认证Token
         * 
         * @param request 订阅请求，包含planId和billingType
         * @return 订阅响应
         */
        @PostMapping("/subscribe")
        SubscribeResponse subscribe(@RequestBody SubscribeRequest request);

        /**
         * 取消订阅
         * 需要用户认证Token
         * 
         * @return 通用响应
         */
        @PostMapping("/cancel")
        SubscribeResponse cancelSubscription();

        /**
         * 购买补充包
         * 需要用户认证Token
         * 
         * @param request 购买补充包请求，包含addonId和quantity
         * @return 购买补充包响应
         */
        @PostMapping("/addons/purchase")
        PurchaseAddonResponse purchaseAddon(@RequestBody PurchaseAddonRequest request);

        /**
         * 记录使用情况
         * 需要用户认证Token
         * 
         * @param request 记录使用请求，包含usageType和quantity
         * @return 记录使用响应
         */
        @PostMapping("/usage/record")
        RecordUsageResponse recordUsage(@RequestBody RecordUsageRequest request);

        /**
         * 获取当前订阅信息
         * 需要用户认证Token
         * 
         * @return 当前订阅响应
         */
        @GetMapping("/current")
        GetCurrentSubscriptionResponse getCurrentSubscription();

        /**
         * 获取用户已购买的补充包列表
         * 需要用户认证Token
         * 
         * @return 用户补充包列表响应
         */
        @GetMapping("/addons/my")
        GetMyAddonsResponse getMyAddons();

        /**
         * 按状态获取用户补充包列表
         * 需要用户认证Token
         * 
         * @param status 补充包状态（active/expired/used）
         * @return 用户补充包列表响应
         */
        @GetMapping("/addons/my")
        GetMyAddonsResponse getMyAddonsByStatus(@RequestParam("status") String status);

        /**
         * 获取用户使用情况（配额统计）
         * 需要用户认证Token
         * 
         * @return 用户使用情况响应
         */
        @GetMapping("/usage")
        GetUsageResponse getUsage();

        /**
         * 获取订阅日志（订阅记录）
         * 需要用户认证Token
         * 
         * @param pageNo   页码（可选，默认1）
         * @param pageSize 每页大小（可选，默认20）
         * @return 日志响应
         */
        @GetMapping("/logs/subscription")
        LogsResponse getSubscriptionLogs(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                        @RequestParam(value = "pageSize", required = false) Integer pageSize);

        /**
         * 获取购买日志（补充包购买记录）
         * 需要用户认证Token
         * 
         * @param pageNo   页码（可选，默认1）
         * @param pageSize 每页大小（可选，默认20）
         * @return 日志响应
         */
        @GetMapping("/logs/purchase")
        LogsResponse getPurchaseLogs(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                        @RequestParam(value = "pageSize", required = false) Integer pageSize);

        /**
         * 获取使用日志（资源使用记录）
         * 需要用户认证Token
         * 
         * @param pageNo   页码（可选，默认1）
         * @param pageSize 每页大小（可选，默认20）
         * @return 日志响应
         */
        @GetMapping("/logs/usage")
        LogsResponse getUsageLogs(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                        @RequestParam(value = "pageSize", required = false) Integer pageSize);

        /**
         * 根据用户信息获取客户用户信息
         * 
         * @param userInfo 用户信息
         * @return ClientUser 客户用户信息
         */
        @GetMapping("/plans")
        ClientUser getClientUserByUserInfo(String userInfo);
}
