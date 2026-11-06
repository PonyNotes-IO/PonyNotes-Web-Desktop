-- 在会员 PostgreSQL 数据库（SLAVE 数据源）执行，仅供新增 wechat_app 支付。
-- 会员变更与该记录同事务提交；MySQL订单更新失败时重试不会重复续期。
CREATE TABLE IF NOT EXISTS wechat_app_fulfillment (
    order_no varchar(32) PRIMARY KEY,
    subscription_id bigint,
    created_at timestamptz NOT NULL DEFAULT now()
);
