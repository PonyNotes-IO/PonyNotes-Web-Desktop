-- 查询系统管理下的菜单配置
SELECT menu_id, menu_name, parent_id, path, component, menu_type, visible, status
FROM sys_menu 
WHERE parent_id = 1 AND menu_type = 'C'
ORDER BY order_num;
