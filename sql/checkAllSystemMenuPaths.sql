-- 查询所有系统管理菜单的 path 配置
SELECT menu_id, menu_name, parent_id, path, component, menu_type, visible, status, perms, icon, order_num
FROM sys_menu 
WHERE parent_id = 1 AND menu_type = 'C'
ORDER BY order_num;
