package com.ruoyi.xmbj.service;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.xmbj.domain.*;
import com.ruoyi.xmbj.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 订阅相关业务逻辑 - 数据库版本
 * 替代了原来调用XMBJ API的方式，所有数据从本地数据库读取
 * 
 * 注意：所有查询操作都配置为使用 PostgreSQL 从库数据源（@DataSource(DataSourceType.SLAVE)）
 * 所有写入操作使用主库数据源（MySQL）
 */
@Slf4j
@Service
@Transactional
public class SubscriptionService {

    @Autowired
    private AfSubscriptionPlansMapper afSubscriptionPlansMapper;

    @Autowired
    private AfSubscriptionAddonsMapper afSubscriptionAddonsMapper;

    @Autowired
    private AfUserSubscriptionsMapper afUserSubscriptionsMapper;

    @Autowired
    private AfUserAddonsMapper afUserAddonsMapper;

    @Autowired
    private UserUsageMapper userUsageMapper;

    // ==================== 订阅计划相关操作 ====================

    /**
     * 获取所有可用的订阅计划
     * 原来: ponynotesService.getSubscriptionPlans() API调用
     * 现在: 从 PostgreSQL 从库读取
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionPlans> getSubscriptionPlans() {
        log.info("从 PostgreSQL 从库查询所有活跃的订阅计划");
        return afSubscriptionPlansMapper.selectActiveSubscriptionPlans();
    }

    /**
     * 按ID获取订阅计划
     */
    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionPlans getSubscriptionPlanById(Long planId) {
        return afSubscriptionPlansMapper.selectById(planId);
    }

    /**
     * 按名称获取订阅计划
     */
    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionPlans getSubscriptionPlanByName(String planName) {
        return afSubscriptionPlansMapper.selectByName(planName);
    }

    // ==================== 补充包相关操作 ====================

    /**
     * 获取所有可用的补充包
     * 原来: ponynotesService.getAddons() API调用
     * 现在: 从数据库读取
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionAddons> getAllAddons() {
        log.info("从数据库查询所有活跃的补充包");
        return afSubscriptionAddonsMapper.selectActiveAddons();
    }

    /**
     * 按类型获取补充包
     * 原来: ponynotesService.getAddonsByType() API调用
     * 现在: 从数据库读取
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionAddons> getAddonsByType(String type) {
        log.info("从数据库查询类型为 {} 的补充包", type);
        return afSubscriptionAddonsMapper.selectAddonsByType(type);
    }

    /**
     * 按ID获取补充包
     */
    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionAddons getAddonById(Long addonId) {
        return afSubscriptionAddonsMapper.selectById(addonId);
    }

    /**
     * 新增补充包
     */
    public int addAfSubscriptionAddons(AfSubscriptionAddons addon) {
        log.info("新增补充包: {}", addon.getAddonCode());
        addon.setCreatedAt(new Date());
        addon.setUpdatedAt(new Date());
        return afSubscriptionAddonsMapper.insert(addon);
    }

    /**
     * 删除补充包
     */
    public int deleteAfSubscriptionAddons(Long id) {
        log.info("删除补充包 ID: {}", id);
        return afSubscriptionAddonsMapper.deleteById(id);
    }

    // ==================== 用户订阅相关操作 ====================

    /**
     * 创建用户订阅
     * 原来: ponynotesService.subscribe() API调用
     * 现在: 直接写入本地数据库
     */
    public AfUserSubscriptions subscribe(Long userId, Long planId, String billingType) {
        log.info("用户 {} 订阅计划 {} (计费方式: {})", userId, planId, billingType);

        // 验证订阅计划
        AfSubscriptionPlans plan = afSubscriptionPlansMapper.selectById(planId);
        if (plan == null) {
            throw new RuntimeException("订阅计划不存在");
        }

        // 取消之前的订阅（如果存在）
        AfUserSubscriptions currentSubscription = afUserSubscriptionsMapper.selectCurrentActiveSubscription(userId);
        if (currentSubscription != null) {
            currentSubscription.setStatus("cancelled");
            currentSubscription.setUpdatedAt(LocalDateTime.now());
            afUserSubscriptionsMapper.updateById(currentSubscription);
        }

        // 创建新订阅
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = calculateSubscriptionEndDate(now, billingType);

        AfUserSubscriptions subscription = AfUserSubscriptions.builder()
                .uid(userId)
                .planId(planId)
                .startDate(now)
                .endDate(endDate)
                .status("active")
                .createdAt(now)
                .updatedAt(now)
                .build();

        afUserSubscriptionsMapper.insert(subscription);

        // 初始化用户使用统计（如果不存在）
        UserUsage usage = userUsageMapper.selectUserUsage(userId);
        if (usage == null) {
            UserUsage newUsage = UserUsage.builder()
                    .userId(userId)
                    .storageUsed(0L)
                    .aiCallsMade(0)
                    .priorityCount(0)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            userUsageMapper.insert(newUsage);
        }

        log.info("用户 {} 订阅成功，订阅ID: {}", userId, subscription.getId());
        return subscription;
    }

    /**
     * 取消用户订阅
     * 原来: ponynotesService.cancelSubscription() API调用
     * 现在: 更新本地数据库
     */
    public void cancelSubscription(Long userId) {
        log.info("取消用户 {} 的订阅", userId);
        AfUserSubscriptions subscription = afUserSubscriptionsMapper.selectCurrentActiveSubscription(userId);
        if (subscription != null) {
            subscription.setStatus("cancelled");
            subscription.setUpdatedAt(LocalDateTime.now());
            afUserSubscriptionsMapper.updateById(subscription);
        }
    }

    /**
     * 获取用户当前订阅
     * 原来: ponynotesService.getCurrentSubscription() API调用
     * 现在: 从数据库读取
     */
    @DataSource(DataSourceType.SLAVE)
    public AfUserSubscriptions getCurrentSubscription(Long userId) {
        // 检查并更新已过期的订阅
//        afUserSubscriptionsMapper.updateExpiredSubscriptions(userId);

        return afUserSubscriptionsMapper.selectCurrentActiveSubscription(userId);
    }

    /**
     * 检查用户是否有有效的订阅
     */
    @DataSource(DataSourceType.SLAVE)
    public boolean hasValidSubscription(Long userId) {
        AfUserSubscriptions subscription = getCurrentSubscription(userId);
        if (subscription == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        return subscription.getEndDate().isAfter(now);
    }

    /**
     * 获取用户订阅历史
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfUserSubscriptions> getSubscriptionHistory(Long userId) {
        return afUserSubscriptionsMapper.selectUserSubscriptions(userId);
    }

    // ==================== 补充包购买相关操作 ====================

    /**
     * 购买补充包
     * 原来: ponynotesService.purchaseAddon() API调用
     * 现在: 直接写入本地数据库
     */
    public AfUserAddons purchaseAddon(Long userId, Long addonId, Integer quantity) {
        log.info("用户 {} 购买补充包 {} (数量: {})", userId, addonId, quantity);

        // 验证补充包
        AfSubscriptionAddons addon = afSubscriptionAddonsMapper.selectById(addonId);
        if (addon == null) {
            throw new RuntimeException("补充包不存在");
        }

        // 创建购买记录
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = calculateAddonExpirationDate(now);

        AfUserAddons userAddon = AfUserAddons.builder()
                .uid(userId)
                .addonId(addonId)
                .quantity(quantity)
                .startDate(now)
                .endDate(expirationDate)
                .status("active")
                .createdAt(now)
                .updatedAt(now)
                .build();

        afUserAddonsMapper.insert(userAddon);

        log.info("用户 {} 购买补充包成功，记录ID: {}", userId, userAddon.getId());
        return userAddon;
    }

    /**
     * 获取用户所有活跃的补充包
     * 原来: ponynotesService.getMyAddons() API调用
     * 现在: 从数据库读取
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfUserAddons> getMyAddons(Long userId) {
        // 检查并更新已过期的补充包
        afUserAddonsMapper.updateExpiredAddons(userId);

        return afUserAddonsMapper.selectActiveUserAddons(userId);
    }

    /**
     * 按状态获取用户补充包
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfUserAddons> getMyAddonsByStatus(Long userId, String status) {
        return afUserAddonsMapper.selectUserAddonsByStatus(userId, status);
    }

    /**
     * 获取用户已购买的特定补充包
     */
    @DataSource(DataSourceType.SLAVE)
    public AfUserAddons getUserAddon(Long userId, Long addonId) {
        return afUserAddonsMapper.selectUserAddon(userId, addonId);
    }

    // ==================== 使用统计相关操作 ====================

    /**
     * 获取用户使用统计
     * 原来: ponynotesService.getUsage() API调用
     * 现在: 从数据库读取
     */
    @DataSource(DataSourceType.SLAVE)
    public UserUsage getUsage(Long userId) {
        log.info("获取用户 {} 的使用统计", userId);
        UserUsage usage = userUsageMapper.selectUserUsage(userId);
        if (usage == null) {
            log.warn("用户 {} 的使用统计不存在，创建新记录", userId);
            UserUsage newUsage = UserUsage.builder()
                    .userId(userId)
                    .storageUsed(0L)
                    .aiCallsMade(0)
                    .priorityCount(0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userUsageMapper.insert(newUsage);
            return newUsage;
        }
        return usage;
    }

    /**
     * 获取订阅计划（分页）
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionPlans> getSubscriptionPlansPaged() {
        List<AfSubscriptionPlans> list = afSubscriptionPlansMapper.selectActiveSubscriptionPlans();
        return list;
    }

    /**
     * 获取订阅计划（分页） - 带参数版本
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionPlans> getSubscriptionPlansPaged(int page, int size) {
        return getSubscriptionPlansPaged(); // 调用无参数版本
    }

    /**
     * 获取用户订阅历史（分页）
     */
    @DataSource(DataSourceType.SLAVE)
    public List<AfUserSubscriptions> getSubscriptionHistoryPaged(Long userId) {
        List<AfUserSubscriptions> list = afUserSubscriptionsMapper.selectUserSubscriptions(userId);
        return list;
    }

    /**
     * 记录用户使用量
     * 原来: ponynotesService.recordUsage() API调用
     * 现在: 直接写入本地数据库
     */
    @DataSource(DataSourceType.SLAVE)
    public void recordUsage(Long userId, String usageType, Long quantity) {
        log.info("记录用户 {} 的使用: 类型={}, 数量={}", userId, usageType, quantity);

        UserUsage usage = getUsage(userId);
        if (usage == null) {
            userUsageMapper.initUserUsage(userId);
            usage = userUsageMapper.selectUserUsage(userId);
        }

        if ("storage".equalsIgnoreCase(usageType)) {
            userUsageMapper.addStorageUsage(userId, quantity);
        } else if ("ai_call".equalsIgnoreCase(usageType)) {
            userUsageMapper.addAiCalls(userId, quantity.intValue());
        }

        log.info("使用量记录成功");
    }

    /**
     * 增加存储使用量
     */
    public void addStorageUsage(Long userId, Long bytes) {
        recordUsage(userId, "storage", bytes);
    }

    /**
     * 增加AI调用次数
     */
    public void addAiCalls(Long userId, Integer count) {
        recordUsage(userId, "ai_call", count.longValue());
    }

    // ==================== 辅助方法 ====================

    /**
     * 计算订阅结束日期
     */
    @DataSource(DataSourceType.SLAVE)
    private LocalDateTime calculateSubscriptionEndDate(LocalDateTime startDate, String billingType) {
        if ("monthly".equalsIgnoreCase(billingType)) {
            return startDate.plusMonths(1);
        } else if ("annual".equalsIgnoreCase(billingType)) {
            return startDate.plusYears(1);
        }
        // 免费计划默认30天
        return startDate.plusDays(30);
    }

    /**
     * 计算补充包过期日期（默认90天）
     */
    @DataSource(DataSourceType.SLAVE)
    private LocalDateTime calculateAddonExpirationDate(LocalDateTime purchaseDate) {
        return purchaseDate.plusDays(90);
    }

    @DataSource(DataSourceType.SLAVE)
    public AfUserSubscriptions selectUserSubscription(String clientSubscriptionId) {
        return afUserSubscriptionsMapper.selectUserSubscriptionById(Long.valueOf(clientSubscriptionId));
    }

    @DataSource(DataSourceType.SLAVE)
    public AfUserAddons selectUserAddon(String clientUserAddonId) {
        return afUserAddonsMapper.selectUserAddonById(Long.valueOf(clientUserAddonId));
    }

    @DataSource(DataSourceType.SLAVE)
    public int update(AfUserAddons item) {
        return afUserAddonsMapper.updateById(item);
    }

    @DataSource(DataSourceType.SLAVE)
    public int updateAfSubscriptionAddons(AfSubscriptionAddons item) {
        return afSubscriptionAddonsMapper.updateAfSubscriptionAddons(item);
    }

    public int updateAfSubscriptionPlans(AfSubscriptionPlans item) {
        return afSubscriptionPlansMapper.updateById(item);
    }

    @DataSource(DataSourceType.SLAVE)
    public AfSubscriptionPlans getAfSubscriptionPlansById(String planId) {
        return afSubscriptionPlansMapper.selectById(Long.valueOf(planId));
    }

    @DataSource(DataSourceType.SLAVE)
    public List<AfSubscriptionAddons> getAfSubscriptionAddons(String addonId) {
        return afSubscriptionAddonsMapper.selectByIds(addonId);
    }
}
