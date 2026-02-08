CREATE TABLE `app_version` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键（序号）',
  `version_name` varchar(64) NOT NULL COMMENT '版本名称（如V2.3.0）',
  `version_type` varchar(32) NOT NULL COMMENT '版本类型（如正式版、测试版、灰度版）',
  `version_code` int(11) NOT NULL COMMENT '版本号（整型，用于版本比较，如20300）',
  `update_desc` text COMMENT '更新描述（文件链接）',
  `update_desc_file` varchar(255) DEFAULT NULL COMMENT '更新描述文件路径',
  `package_url` varchar(500) NOT NULL COMMENT '上传文件包（安装包）下载链接',
  `system_type` varchar(16) NOT NULL COMMENT '系统类型（android/ios）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0-未发布 1-已发布 2-已停用）',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `operator` varchar(32) NOT NULL COMMENT '操作人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常 1-已删除）',
  PRIMARY KEY (`id`),
  KEY `idx_system_version` (`system_type`,`version_code`) COMMENT '按系统类型+版本号查询索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='App版本管理表';
