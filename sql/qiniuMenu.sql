-- 七牛云存储管理菜单
-- 父菜单ID：系统管理（1）
-- 查询系统管理的菜单ID
SELECT menu_id, menu_name, parent_id, order_num 
FROM sys_menu 
WHERE menu_name = '系统管理' AND parent_id = 0;

-- 插入七牛云存储管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('七牛云存储', 1, 9, 'qiniu', 'system/qiniu/index', 1, 0, 'C', '0', '0', 'system:qiniu:list', 'cloud', 'admin', NOW(), '', NULL, '七牛云存储管理菜单');

-- 获取刚插入的菜单ID（假设为最新的菜单ID）
-- 实际使用时需要根据实际情况调整

-- 查询七牛云存储管理菜单的ID
SELECT menu_id, menu_name, parent_id, order_num 
FROM sys_menu 
WHERE menu_name = '七牛云存储' AND parent_id = 1;

-- 插入存储信息查询按钮
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('存储信息查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '七牛云存储' AND parent_id = 1 LIMIT 1), 1, '', NULL, 1, 0, 'F', '0', '0', 'system:qiniu:query', '#', 'admin', NOW(), '', NULL, '');

-- 插入文件列表查询按钮
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('文件列表查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '七牛云存储' AND parent_id = 1 LIMIT 1), 2, '', NULL, 1, 0, 'F', '0', '0', 'system:qiniu:query', '#', 'admin', NOW(), '', NULL, '');

-- 查询插入的菜单
SELECT menu_id, menu_name, parent_id, order_num, perms 
FROM sys_menu 
WHERE menu_name IN ('七牛云存储', '存储信息查询', '文件列表查询')
ORDER BY parent_id, order_num;
