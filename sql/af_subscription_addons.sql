CREATE TABLE af_subscription_addons (
  id serial PRIMARY KEY,
  addon_code varchar(50) NOT NULL UNIQUE,
  addon_name varchar(100) NOT NULL,
  addon_name_cn varchar(100) NOT NULL,
  addon_type varchar(20) NOT NULL,
  price_yuan decimal(10,2) NOT NULL,
  storage_gb integer,
  ai_chat_count integer,
  ai_image_count integer,
  is_active boolean NOT NULL DEFAULT true,
  created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamptz DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE af_subscription_addons IS '订阅补充包表';
COMMENT ON COLUMN af_subscription_addons.id IS '补充包ID';
COMMENT ON COLUMN af_subscription_addons.addon_code IS '补充包代码';
COMMENT ON COLUMN af_subscription_addons.addon_name IS '补充包名称';
COMMENT ON COLUMN af_subscription_addons.addon_name_cn IS '补充包中文名称';
COMMENT ON COLUMN af_subscription_addons.addon_type IS '补充包类型（storage/ai_token）';
COMMENT ON COLUMN af_subscription_addons.price_yuan IS '价格（元）';
COMMENT ON COLUMN af_subscription_addons.storage_gb IS '存储空间（GB，仅当addon_type=storage时有效）';
COMMENT ON COLUMN af_subscription_addons.ai_chat_count IS 'AI对话次数（仅当addon_type=ai_token时有效）';
COMMENT ON COLUMN af_subscription_addons.ai_image_count IS 'AI图片生成次数（仅当addon_type=ai_token时有效）';
COMMENT ON COLUMN af_subscription_addons.is_active IS '是否激活';
COMMENT ON COLUMN af_subscription_addons.created_at IS '创建时间';
COMMENT ON COLUMN af_subscription_addons.updated_at IS '更新时间';

CREATE INDEX idx_addon_code ON af_subscription_addons(addon_code);
CREATE INDEX idx_addon_type ON af_subscription_addons(addon_type);

INSERT INTO af_subscription_addons (addon_code, addon_name, addon_name_cn, addon_type, price_yuan, storage_gb, ai_chat_count, ai_image_count, is_active) VALUES
('storage_5gb', '5GB Storage', '5GB存储包', 'storage', 9.90, 5, NULL, NULL, true),
('storage_20gb', '20GB Storage', '20GB存储包', 'storage', 29.90, 20, NULL, NULL, true),
('storage_50gb', '50GB Storage', '50GB存储包', 'storage', 69.90, 50, NULL, NULL, true),
('ai_token_100', '100 AI Tokens', '100次AI对话包', 'ai_token', 19.90, NULL, 100, 20, true),
('ai_token_400', '400 AI Tokens', '400次AI对话包', 'ai_token', 59.90, NULL, 400, 80, true),
('ai_token_1000', '1000 AI Tokens', '1000次AI对话包', 'ai_token', 149.90, NULL, 1000, 200, true);
