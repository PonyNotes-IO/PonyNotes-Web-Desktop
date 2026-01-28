insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅补充包', '1061', '5', 'subscriptionAddons', 'xmbj/subscriptionAddons/index', 1, 0, 'C', '0', '0', 'xmbj:subscriptionAddons:list', '#', 'admin', CURRENT_TIMESTAMP, '', null, '订阅补充包菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅补充包查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 
'xmbj:subscriptionAddons:query',        '#', 'admin', CURRENT_TIMESTAMP, '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅补充包新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 
'xmbj:subscriptionAddons:add',          '#', 'admin', CURRENT_TIMESTAMP, '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅补充包修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 
'xmbj:subscriptionAddons:edit',         '#', 'admin', CURRENT_TIMESTAMP, '', null, '');     

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅补充包删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:subscriptionAddons:remove',       '#', 'admin', CURRENT_TIMESTAMP, '', null, '');    

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订阅补充包导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:subscriptionAddons:export',       '#', 'admin', CURRENT_TIMESTAMP, '', null, '');    
