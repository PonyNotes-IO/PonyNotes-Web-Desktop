package com.ruoyi.xmbj.api.service;

import com.ruoyi.xmbj.api.protocol.ClientUser;
import com.ruoyi.xmbj.api.protocol.subscription.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * XMBJ 订阅管理服务
 * 
 * 功能：
 * 1. 为用户创建或更新订阅
 * 2. 管理用户的补充包购买
 * 3. 获取用户的订阅和使用情况
 * 4. 处理订阅相关的业务逻辑
 * 
 * 该服务是 PonynotesService (FeignClient) 的业务层包装，
 * 提供更高级的业务方法和错误处理
 * 
 * @author ruoyi
 */
@Service
public class XmbjSubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(XmbjSubscriptionService.class);

    @Autowired
    private PonynotesService ponynotesService;

    @Autowired
    private PonynotesSubscriptionService ponynotesSubscriptionService;

    @Autowired
    private XmbjAuthService xmbjAuthService;

//    /**
//     * 获取所有可用的订阅计划
//     *
//     * @return 订阅计划列表及其详细信息
//     */
//    public GetSubscriptionPlansResponse getSubscriptionPlans() {
//        try {
//            log.info("获取订阅计划列表");
//            GetSubscriptionPlansResponse response = ponynotesService.getSubscriptionPlans();
//
//            if (!(response.getCode()==0)) {
//                log.warn("获取订阅计划失败: {}", response.getMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("获取订阅计划异常", e);
//            throw new RuntimeException("Failed to get subscription plans: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 获取所有可用的补充包
//     *
//     * @return 补充包列表（存储、AI 配额、优先级等）
//     */
//    public GetAddonsResponse getAllAddons() {
//        try {
//            log.info("获取所有补充包");
//            GetAddonsResponse response = ponynotesService.getAddons();
//
//            if (!response.isSuccess()) {
//                log.warn("获取补充包失败: {}", response.getMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("获取补充包异常", e);
//            throw new RuntimeException("Failed to get addons: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 按类型获取补充包
//     *
//     * 支持的类型：
//     * - storage: 存储扩展
//     * - ai_quota: AI 配额
//     * - priority: 优先级
//     *
//     * @param addonType 补充包类型
//     * @return 指定类型的补充包列表
//     */
//    public GetAddonsResponse getAddonsByType(String addonType) {
//        try {
//            log.info("获取补充包，类型: {}", addonType);
//            GetAddonsResponse response = ponynotesService.getAddonsByType(addonType);
//
//            if (!response.isSuccess()) {
//                log.warn("按类型获取补充包失败，类型: {}, 错误: {}", addonType, response.getErrorMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("按类型获取补充包异常，类型: {}", addonType, e);
//            throw new RuntimeException("Failed to get addons by type: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 为用户创建新订阅
//     *
//     * 流程：
//     * 1. 检查用户认证 token 是否有效
//     * 2. 调用 XMBJ API 创建订阅
//     * 3. 记录操作日志
//     *
//     * 需要用户认证 token（会通过 Feign 拦截器自动添加）
//     *
//     * @param userId      用户 ID（用于验证 token）
//     * @param planId      订阅计划 ID
//     * @param billingType 计费周期（monthly/annual）
//     * @return 订阅响应，包含新建的订阅信息
//     */
//    public SubscribeResponse subscribe(Long userId, Long planId, String billingType) {
//        try {
//            // 验证用户是否有有效的 XMBJ token
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token. Please login to XMBJ first.");
//            }
//
//            log.info("为用户创建订阅，用户ID: {}, 计划ID: {}, 计费方式: {}", userId, planId, billingType);
//
//            SubscribeRequest request = new SubscribeRequest();
//            request.setPlan_id(planId);
//            request.setBilling_type(billingType);
//
//            SubscribeResponse response = ponynotesService.subscribe(request);
//
//            if (!response.isSuccess()) {
//                log.error("创建订阅失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//            } else {
//                log.info("订阅创建成功，用户ID: {}, 订阅ID: {}", userId, response.getSubscription().getId());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("创建订阅异常，用户ID: {}", userId, e);
//            throw new RuntimeException("Failed to create subscription: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 取消用户当前的订阅
//     *
//     * 注意：
//     * - 取消后用户的访问权限可能受限
//     * - 可能产生额外的费用（取决于 XMBJ 的政策）
//     *
//     * @param userId 用户 ID
//     * @return 取消结果
//     */
//    public SubscribeResponse cancelSubscription(Long userId) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token.");
//            }
//
//            log.info("取消用户订阅，用户ID: {}", userId);
//
//            SubscribeResponse response = ponynotesService.cancelSubscription();
//
//            if (!response.isSuccess()) {
//                log.error("取消订阅失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//            } else {
//                log.info("订阅取消成功，用户ID: {}", userId);
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("取消订阅异常，用户ID: {}", userId, e);
//            throw new RuntimeException("Failed to cancel subscription: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 购买补充包
//     *
//     * 补充包类型：
//     * - 存储扩展：扩展云存储容量
//     * - AI 配额：增加 AI 功能使用次数
//     * - 优先级：获得优先处理权
//     *
//     * @param userId   用户 ID
//     * @param addonId  补充包 ID
//     * @param quantity 购买数量
//     * @return 购买响应
//     */
//    public PurchaseAddonResponse purchaseAddon(Long userId, Long addonId, Integer quantity) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token.");
//            }
//
//            if (quantity == null || quantity <= 0) {
//                throw new IllegalArgumentException("Quantity must be greater than 0");
//            }
//
//            log.info("购买补充包，用户ID: {}, 补充包ID: {}, 数量: {}", userId, addonId, quantity);
//
//            PurchaseAddonRequest request = new PurchaseAddonRequest();
//            request.setAddon_id(addonId);
//            request.setQuantity(quantity);
//
//            PurchaseAddonResponse response = ponynotesService.purchaseAddon(request);
//
//            if (!response.isSuccess()) {
//                log.error("购买补充包失败，用户ID: {}, 补充包ID: {}, 错误: {}", userId, addonId, response.getErrorMessage());
//            } else {
//                log.info("补充包购买成功，用户ID: {}, 补充包ID: {}, 数量: {}", userId, addonId, quantity);
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("购买补充包异常，用户ID: {}, 补充包ID: {}", userId, addonId, e);
//            throw new RuntimeException("Failed to purchase addon: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 获取用户当前的订阅信息
//     *
//     * 包含：
//     * - 订阅计划名称
//     * - 订阅开始和结束日期
//     * - 当前配额信息
//     * - 订阅状态
//     *
//     * @param userId 用户 ID
//     * @return 用户的当前订阅信息，如果无订阅则返回 null
//     */
//    public UserSubscription getCurrentSubscription(Long userId) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                return null;
//            }
//
//            log.debug("获取用户当前订阅，用户ID: {}", userId);
//
//            GetCurrentSubscriptionResponse response = ponynotesService.getCurrentSubscription();
//
//            if (!response.isSuccess()) {
//                log.debug("获取当前订阅失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//                return null;
//            }
//
//            UserSubscription subscription = response.getSubscription();
//
//            // 检查订阅是否有效
//            if (subscription != null && !isSubscriptionExpired(subscription)) {
//                log.debug("用户已有有效订阅，用户ID: {}, 过期时间: {}", userId, subscription.getEnd_date());
//            } else {
//                log.debug("用户订阅已过期或不存在，用户ID: {}", userId);
//            }
//
//            return subscription;
//        } catch (Exception e) {
//            log.error("获取当前订阅异常，用户ID: {}", userId, e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取用户已购的补充包列表
//     *
//     * @param userId 用户 ID
//     * @param status 可选：过滤状态（active/expired/all）
//     * @return 用户已购的补充包列表
//     */
//    public GetMyAddonsResponse getMyAddons(Long userId, String status) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token.");
//            }
//
//            log.debug("获取用户已购补充包，用户ID: {}", userId);
//
//            GetMyAddonsResponse response;
//
//            if (status != null && !status.isEmpty()) {
//                response = ponynotesService.getMyAddonsByStatus(status);
//            } else {
//                response = ponynotesService.getMyAddons();
//            }
//
//            if (!response.isSuccess()) {
//                log.warn("获取用户补充包失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("获取用户补充包异常，用户ID: {}", userId, e);
//            throw new RuntimeException("Failed to get user addons: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 获取用户的使用情况统计
//     *
//     * 包含：
//     * - 存储容量使用情况
//     * - AI 功能使用次数
//     * - 其他配额使用情况
//     *
//     * @param userId 用户 ID
//     * @return 使用统计信息
//     */
//    public GetUsageResponse getUsage(Long userId) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token.");
//            }
//
//            log.debug("获取用户使用情况，用户ID: {}", userId);
//
//            GetUsageResponse response = ponynotesService.getUsage();
//
//            if (!response.isSuccess()) {
//                log.warn("获取使用情况失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("获取使用情况异常，用户ID: {}", userId, e);
//            throw new RuntimeException("Failed to get usage: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 记录用户的使用情况（增加计数）
//     *
//     * 用途：
//     * - 记录 AI 功能使用次数
//     * - 记录存储增加量
//     * - 记录其他资源使用情况
//     *
//     * @param userId    用户 ID
//     * @param usageType 使用类型（ai_call/storage/etc）
//     * @param quantity  使用数量
//     * @return 记录结果
//     */
//    public RecordUsageResponse recordUsage(Long userId, String usageType, Integer quantity) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token.");
//            }
//
//            log.debug("记录用户使用情况，用户ID: {}, 使用类型: {}, 数量: {}", userId, usageType, quantity);
//
//            RecordUsageRequest request = new RecordUsageRequest();
//            request.setUsage_type(usageType);
//            request.setQuantity(quantity);
//
//            RecordUsageResponse response = ponynotesService.recordUsage(request);
//
//            if (!response.isSuccess()) {
//                log.error("记录使用情况失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//            } else {
//                log.debug("使用情况记录成功，用户ID: {}, 类型: {}, 数量: {}", userId, usageType, quantity);
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("记录使用情况异常，用户ID: {}", userId, e);
//            throw new RuntimeException("Failed to record usage: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 检查用户是否有有效的订阅
//     *
//     * "有效"定义：
//     * 1. 用户存在有效的订阅记录
//     * 2. 订阅未过期
//     *
//     * @param userId 用户 ID
//     * @return true 表示用户有有效订阅；false 表示无有效订阅
//     */
//    public boolean hasValidSubscription(Long userId) {
//        try {
//            UserSubscription subscription = getCurrentSubscription(userId);
//
//            if (subscription == null) {
//                return false;
//            }
//
//            return !isSubscriptionExpired(subscription);
//        } catch (Exception e) {
//            log.error("检查订阅有效性异常，用户ID: {}", userId, e);
//            return false;
//        }
//    }
//
//    /**
//     * 检查订阅是否已过期
//     *
//     * @param subscription 订阅信息
//     * @return true 表示已过期；false 表示未过期
//     */
//    private boolean isSubscriptionExpired(UserSubscription subscription) {
//        if (subscription == null || subscription.getEnd_date() == null) {
//            return true;
//        }
//
//        try {
//            // 简单的日期字符串比较
//            // 实际应该转换为 LocalDateTime 并比较
//            String endDate = subscription.getEnd_date();
//            String now = LocalDateTime.now().toString().substring(0, 10); // "YYYY-MM-DD"
//
//            return endDate.compareTo(now) < 0;
//        } catch (Exception e) {
//            log.warn("检查订阅过期时间异常，默认认为已过期", e);
//            return true;
//        }
//    }
//
//    /**
//     * 获取订阅日志
//     *
//     * @param userId   用户 ID
//     * @param pageNo   页码
//     * @param pageSize 每页大小
//     * @return 订阅日志列表
//     */
//    public LogsResponse getSubscriptionLogs(Long userId, Integer pageNo, Integer pageSize) {
//        try {
//            if (!xmbjAuthService.hasValidAccessToken(userId)) {
//                log.warn("用户无有效的 XMBJ access_token，用户ID: {}", userId);
//                throw new RuntimeException("User has no valid XMBJ access token.");
//            }
//
//            log.debug("获取订阅日志，用户ID: {}, 页码: {}, 页大小: {}", userId, pageNo, pageSize);
//
//            LogsResponse response = ponynotesService.getSubscriptionLogs(pageNo, pageSize);
//
//            if (!response.isSuccess()) {
//                log.warn("获取订阅日志失败，用户ID: {}, 错误: {}", userId, response.getErrorMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.error("获取订阅日志异常，用户ID: {}", userId, e);
//            throw new RuntimeException("Failed to get subscription logs: " + e.getMessage(), e);
//        }
//    }
//
//    public ClientUser getClientUserByUserInfo(String userInfo) {
//
//    }
}
