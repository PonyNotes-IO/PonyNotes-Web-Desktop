-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('第三方登录绑定', '1061', '1', 'bind', 'system/bind/index', 1, 0, 'C', '0', '0', 'system:bind:list', '#', 'admin', sysdate(), '', null, '第三方登录绑定菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('第三方登录绑定查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:bind:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('第三方登录绑定新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:bind:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('第三方登录绑定修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:bind:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('第三方登录绑定删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:bind:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('第三方登录绑定导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:bind:export',       '#', 'admin', sysdate(), '', null, '');