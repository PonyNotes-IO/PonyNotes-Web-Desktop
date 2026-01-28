-- 修改 ai_models 表的 status 字段类型为 tinyint
-- 先检查表是否存在
-- ALTER TABLE ai_models MODIFY COLUMN status tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0-未发布 1-已发布 2-已停用）';

-- 更新现有的 status 数据
-- 将 '正常' 或其他字符串状态转换为数字
UPDATE ai_models SET status = 1 WHERE status = '正常' OR status = '已发布';
UPDATE ai_models SET status = 0 WHERE status = '未发布';
UPDATE ai_models SET status = 2 WHERE status = '已停用';

-- 修改字段类型
ALTER TABLE ai_models MODIFY COLUMN status tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0-未发布 1-已发布 2-已停用）';

-- 验证修改结果
SELECT id, name, status FROM ai_models LIMIT 5;
