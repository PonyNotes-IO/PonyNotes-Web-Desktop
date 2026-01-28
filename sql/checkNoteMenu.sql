SELECT menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon
FROM sys_menu
WHERE menu_name LIKE '%快速笔记%' OR menu_name LIKE '%note%' OR component LIKE '%note%'
ORDER BY parent_id, order_num;
