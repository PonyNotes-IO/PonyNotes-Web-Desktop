insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工作空间', '1061', '2', 'workspace', 'xmbj/workspace/index', 1, 0, 'C', '0', '0', 'xmbj:workspace:list', '#', 'admin', sysdate(), '', null, '工作空间菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工作空间查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:workspace:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工作空间新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:workspace:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工作空间修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:workspace:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工作空间删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:workspace:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工作空间导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:workspace:export',       '#', 'admin', sysdate(), '', null, '');
