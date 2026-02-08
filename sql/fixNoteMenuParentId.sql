UPDATE sys_menu
SET parent_id = 1061
WHERE menu_id = 2021;

SELECT menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon
FROM sys_menu
WHERE menu_id = 2021 OR parent_id = 2021
ORDER BY menu_id;
