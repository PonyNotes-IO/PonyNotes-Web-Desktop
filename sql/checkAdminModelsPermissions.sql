-- 查询管理员角色（role_id=1）的所有 AI 模型管理权限
SELECT rm.role_id, rm.menu_id, m.menu_name, m.perms, m.menu_type
FROM sys_role_menu rm 
JOIN sys_menu m ON rm.menu_id = m.menu_id 
WHERE rm.role_id = 1 
  AND (m.menu_id = 2015 OR m.parent_id = 2015)
ORDER BY m.menu_id;
