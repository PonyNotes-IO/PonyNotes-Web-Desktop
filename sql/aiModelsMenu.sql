insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型管理', '1061', '4', 'models', 'system/models/index', 1, 0, 'C', '0', '0', 'system:models:list', '#', 'admin', sysdate(), '', null, 'AI模型管理菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 
'system:models:query',        '#', 'admin', sysdate(), '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 
'system:models:add',          '#', 'admin', sysdate(), '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 
'system:models:edit',         '#', 'admin', sysdate(), '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:models:remove',       '#', 'admin', sysdate(), '', null, '');    

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:models:export',       '#', 'admin', sysdate(), '', null, '');    
