insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速笔记', '1061', '3', 'note', 'xmbj/note/index', 1, 0, 'C', '0', '0', 'xmbj:note:list', '#', 'admin', sysdate(), '', null, '快速笔记菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速笔记查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:note:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速笔记新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:note:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速笔记修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:note:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速笔记删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:note:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速笔记导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:note:export',       '#', 'admin', sysdate(), '', null, '');
