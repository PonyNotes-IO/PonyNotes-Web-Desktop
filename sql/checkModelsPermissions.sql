-- 查询 AI 模型管理的菜单权限
SELECT menu_id, menu_name, perms FROM sys_menu WHERE menu_id = 2015;

-- 查询所有角色
SELECT role_id, role_name, role_key FROM sys_role;

-- 查询管理员角色是否已有 AI 模型管理权限
SELECT rm.role_id, rm.menu_id, m.menu_name, m.perms 
FROM sys_role_menu rm 
JOIN sys_menu m ON rm.menu_id = m.menu_id 
WHERE rm.role_id = 1 AND m.menu_id = 2015;
