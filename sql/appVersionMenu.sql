insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本管理', '1061', '3', 'appVersion', 'xmbj/appVersion/index', 1, 0, 'C', '0', '0', 'xmbj:appVersion:list', '#', 'admin', sysdate(), '', null, 'App版本管理菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 
'xmbj:appVersion:query',        '#', 'admin', sysdate(), '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 
'xmbj:appVersion:add',          '#', 'admin', sysdate(), '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 
'xmbj:appVersion:edit',         '#', 'admin', sysdate(), '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:appVersion:remove',       '#', 'admin', sysdate(), '', null, '');    

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:appVersion:export',       '#', 'admin', sysdate(), '', null, '');    

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('App版本上传', @parentId, '6',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:appVersion:upload',       '#', 'admin', sysdate(), '', null, '');    
