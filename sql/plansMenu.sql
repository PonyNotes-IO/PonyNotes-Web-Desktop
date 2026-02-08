-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅套餐', '1061', '1', 'plans', 'xmbj/plans/index', 1, 0, 'C', '0', '0', 'xmbj:plans:list', '#', 'admin', sysdate(), '', null, '订阅套餐菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅套餐查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:plans:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅套餐新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:plans:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅套餐修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:plans:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅套餐删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:plans:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅套餐导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:plans:export',       '#', 'admin', sysdate(), '', null, '');