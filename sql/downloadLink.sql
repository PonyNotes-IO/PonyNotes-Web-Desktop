-- 下载链接配置表
-- 用于配置 xmbj-www-ui /download 页面各平台的下载链接
-- 替代 download.vue 中硬编码的链接，支持后台动态管理

CREATE TABLE IF NOT EXISTS `download_link` (
  `id`            bigint(20)   unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `platform`      varchar(32)  NOT NULL COMMENT '平台（Windows、macOS、Android/Pad、iPhone/iPad）',
  `arch_label`    varchar(64)  DEFAULT NULL COMMENT '架构标签（如：Intel 芯片版 (x86)，仅多架构平台需要填写）',
  `arch_desc`     varchar(128) DEFAULT NULL COMMENT '架构描述（如：适用于 Intel 处理器的 Mac）',
  `version`       varchar(32)  DEFAULT NULL COMMENT '版本号（如：1.0.0）',
  `download_url`  varchar(500) NOT NULL COMMENT '下载链接',
  `sort_order`    int(11)      NOT NULL DEFAULT '0' COMMENT '排序（数字越小越靠前）',
  `status`        tinyint(1)   NOT NULL DEFAULT '1' COMMENT '状态（0-禁用 1-启用）',
  `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `operator`      varchar(32)  DEFAULT NULL COMMENT '操作人（用户ID）',
  `del_flag`      tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常 1-已删除）',
  `remark`        varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_platform_status` (`platform`, `status`) COMMENT '按平台+状态查询索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='下载链接配置表';

-- 初始化数据：迁移自 xmbj-www-ui/app/pages/download.vue 中硬编码的链接
INSERT INTO `download_link` (`platform`, `arch_label`, `arch_desc`, `version`, `download_url`, `sort_order`, `status`, `remark`) VALUES
('macOS', 'Intel 芯片版 (x86)',  '适用于 Intel 处理器的 Mac',     '1.0.0', 'https://example.com/downloads/PonyNotes-1.0.0-intel-x86.dmg',   1, 1, '迁移自 download.vue 硬编码链接，请修改为真实地址'),
('macOS', 'Apple 芯片版 (ARM)',  '适用于 M1/M2/M3 等芯片的 Mac', '1.0.0', 'https://example.com/downloads/PonyNotes-1.0.0-apple-arm64.dmg', 2, 1, '迁移自 download.vue 硬编码链接，请修改为真实地址'),
('Windows', NULL, NULL, NULL, '#', 3, 0, '原 download.vue 中未配置实际链接，待补充');
