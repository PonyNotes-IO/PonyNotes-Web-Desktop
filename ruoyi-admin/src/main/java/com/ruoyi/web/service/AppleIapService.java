package com.ruoyi.web.service;

import com.ruoyi.system.domain.SysPaymentOrder;

import java.util.List;

/**
 * Apple 应用内购买（StoreKit 2）服务
 */
public interface AppleIapService {

    /**
     * 验证客户端提交的签名交易并开通订阅（幂等，重复提交返回原订单）。
     *
     * @param signedTransaction StoreKit 2 Transaction 的 jwsRepresentation
     * @param userInfo          客户端用户 uuid（ClientUser.uuid）
     * @return 已开通/已存在的订单
     */
    SysPaymentOrder verifyTransaction(String signedTransaction, String userInfo);

    /**
     * 恢复购买：批量验证客户端提交的历史交易，只处理本地没有的。
     *
     * @param signedTransactions jwsRepresentation 列表
     * @param userInfo           客户端用户 uuid
     * @return 本地已存在的有效订单列表
     */
    List<SysPaymentOrder> restoreTransactions(List<String> signedTransactions, String userInfo);

    /**
     * 处理 App Store Server Notifications V2 通知（signedPayload）。
     *
     * @return true 表示已处理（返回苹果 200 用）
     */
    boolean handleNotification(String signedPayload);
}
