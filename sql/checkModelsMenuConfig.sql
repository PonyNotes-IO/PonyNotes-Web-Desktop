-- 查询 AI 模型管理菜单的完整配置
SELECT menu_id, menu_name, parent_id, path, component, menu_type, visible, status, perms, icon, order_num
FROM sys_menu 
WHERE menu_id = 2015;
