package com.ruoyi.xmbj.api.service;

import com.ruoyi.xmbj.api.protocol.subscription.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PonyNotes 订阅服务包装类
 * 
 * 提供更便捷的API调用方式，包含错误处理和日志记录
 */
@Service
public class PonynotesSubscriptionService {

    @Autowired
    private PonynotesService ponynotesService;

    /**
     * 获取订阅计划列表
     * 
     * @return 订阅计划列表
     */
    public GetSubscriptionPlansResponse getSubscriptionPlans() {
        return ponynotesService.getSubscriptionPlans();
    }

    /**
     * 获取所有补充包
     * 
     * @return 补充包列表
     */
    public GetAddonsResponse getAllAddons() {
        return ponynotesService.getAddons();
    }

    /**
     * 根据类型获取补充包
     * 
     * @param addonType 补充包类型（storage/ai_quota/priority）
     * @return 补充包列表
     */
    public GetAddonsResponse getAddonsByType(String addonType) {
        return ponynotesService.getAddonsByType(addonType);
    }

    /**
     * 订阅计划
     * 
     * @param planId      计划ID
     * @param billingType 计费类型（monthly/annual）
     * @return 订阅结果
     */
    public SubscribeResponse subscribe(Long planId, String billingType) {
        SubscribeRequest request = new SubscribeRequest(planId, billingType);
        return ponynotesService.subscribe(request);
    }

    /**
     * 取消订阅
     * 
     * @return 取消结果
     */
    public SubscribeResponse cancelSubscription() {
        return ponynotesService.cancelSubscription();
    }

    /**
     * 购买补充包
     * 
     * @param addonId  补充包ID
     * @param quantity 购买数量
     * @return 购买结果
     */
    public PurchaseAddonResponse purchaseAddon(Long addonId, Integer quantity) {
        PurchaseAddonRequest request = new PurchaseAddonRequest(addonId, quantity);
        return ponynotesService.purchaseAddon(request);
    }

    /**
     * 购买补充包（默认数量为1）
     * 
     * @param addonId 补充包ID
     * @return 购买结果
     */
    public PurchaseAddonResponse purchaseAddon(Long addonId) {
        return purchaseAddon(addonId, 1);
    }

    /**
     * 记录资源使用
     * 
     * @param usageType   使用类型（storage/ai_call）
     * @param quantity    使用量
     * @param description 使用描述
     * @return 记录结果
     */
    public RecordUsageResponse recordUsage(String usageType, Integer quantity, String description) {
        RecordUsageRequest request = new RecordUsageRequest(usageType, quantity, description);
        return ponynotesService.recordUsage(request);
    }

    /**
     * 获取当前订阅信息
     * 
     * @return 当前订阅信息
     */
    public GetCurrentSubscriptionResponse getCurrentSubscription() {
        return ponynotesService.getCurrentSubscription();
    }

    /**
     * 获取用户的所有补充包
     * 
     * @return 用户补充包列表
     */
    public GetMyAddonsResponse getMyAddons() {
        return ponynotesService.getMyAddons();
    }

    /**
     * 根据状态获取用户的补充包
     * 
     * @param status 补充包状态（active/expired/used）
     * @return 用户补充包列表
     */
    public GetMyAddonsResponse getMyAddonsByStatus(String status) {
        return ponynotesService.getMyAddonsByStatus(status);
    }

    /**
     * 获取用户使用情况统计
     * 
     * @return 用户使用情况
     */
    public GetUsageResponse getUsage() {
        return ponynotesService.getUsage();
    }

    /**
     * 获取订阅日志
     * 
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @return 订阅日志列表
     */
    public LogsResponse getSubscriptionLogs(Integer pageNo, Integer pageSize) {
        return ponynotesService.getSubscriptionLogs(pageNo, pageSize);
    }

    /**
     * 获取购买日志
     * 
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @return 购买日志列表
     */
    public LogsResponse getPurchaseLogs(Integer pageNo, Integer pageSize) {
        return ponynotesService.getPurchaseLogs(pageNo, pageSize);
    }

    /**
     * 获取使用日志
     * 
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @return 使用日志列表
     */
    public LogsResponse getUsageLogs(Integer pageNo, Integer pageSize) {
        return ponynotesService.getUsageLogs(pageNo, pageSize);
    }

    /**
     * 获取订阅日志（默认第1页，每页20条）
     * 
     * @return 订阅日志列表
     */
    public LogsResponse getSubscriptionLogs() {
        return getSubscriptionLogs(1, 20);
    }

    /**
     * 获取购买日志（默认第1页，每页20条）
     * 
     * @return 购买日志列表
     */
    public LogsResponse getPurchaseLogs() {
        return getPurchaseLogs(1, 20);
    }

    /**
     * 获取使用日志（默认第1页，每页20条）
     * 
     * @return 使用日志列表
     */
    public LogsResponse getUsageLogs() {
        return getUsageLogs(1, 20);
    }
}
