-- 给管理员角色（role_id=1）分配 AI 模型管理的所有权限

-- 查询 AI 模型管理及其子菜单的所有 menu_id
SELECT menu_id, menu_name, perms 
FROM sys_menu 
WHERE menu_id = 2015 
   OR parent_id = 2015;

-- 插入权限（如果不存在）
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id 
FROM sys_menu 
WHERE menu_id = 2015 
   OR parent_id = 2015;

-- 验证权限是否已添加
SELECT rm.role_id, rm.menu_id, m.menu_name, m.perms 
FROM sys_role_menu rm 
JOIN sys_menu m ON rm.menu_id = m.menu_id 
WHERE rm.role_id = 1 AND (m.menu_id = 2015 OR m.parent_id = 2015);
