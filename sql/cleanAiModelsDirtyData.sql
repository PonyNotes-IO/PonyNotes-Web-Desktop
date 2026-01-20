-- 清理 ai_models 表中的脏数据
-- 查看当前数据情况
SELECT id, name, status, operator FROM ai_models LIMIT 10;

-- 查看所有不同的 status 值
SELECT DISTINCT status, COUNT(*) as count FROM ai_models GROUP BY status;

-- 清理脏数据：将所有非数字的 status 值设置为默认值 0（未发布）
UPDATE ai_models SET status = 0 WHERE status NOT IN ('0', '1', '2', 0, 1, 2);

-- 将字符串类型的数字转换为数字类型
UPDATE ai_models SET status = CAST(status AS UNSIGNED) WHERE status IN ('0', '1', '2');

-- 修改字段类型为 tinyint
ALTER TABLE ai_models MODIFY COLUMN status tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0-未发布 1-已发布 2-已停用）';

-- 验证修改结果
SELECT id, name, status FROM ai_models LIMIT 10;
