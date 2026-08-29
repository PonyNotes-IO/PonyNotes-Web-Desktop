-- Apple 应用内支付（IAP）表结构变更
-- 在已有 sys_payment_order 表上增加苹果内购字段
-- 执行库：与 sys_payment_order 相同的业务库

ALTER TABLE sys_payment_order
    ADD COLUMN apple_transaction_id     VARCHAR(64)  DEFAULT NULL COMMENT '苹果内购本次交易ID(幂等键)' AFTER client_subscription_id,
    ADD COLUMN original_transaction_id  VARCHAR(64)  DEFAULT NULL COMMENT '苹果内购原始交易ID(同一订阅续费不变)' AFTER apple_transaction_id,
    ADD COLUMN apple_product_id         VARCHAR(128) DEFAULT NULL COMMENT 'App Store Connect 商品ID' AFTER original_transaction_id;

-- 幂等：同一苹果交易只发货一次
ALTER TABLE sys_payment_order
    ADD UNIQUE INDEX uk_apple_transaction_id (apple_transaction_id);

-- 订单状态新增 refunded（苹果退款/撤销时标记）
-- payment_type 新增取值 apple（无需 DDL，列为字符串）
