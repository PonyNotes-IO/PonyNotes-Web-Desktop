-- 下载链接配置菜单
-- 父菜单 ID 1061 为 xmbj 一级菜单

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('下载链接配置', '1061', '6', 'downloadLink', 'xmbj/downloadLink/index', 1, 0, 'C', '0', '0', 'xmbj:downloadLink:list', 'download', 'admin', sysdate(), '', null, '下载链接配置菜单');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('下载链接查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:downloadLink:query',  '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('下载链接新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:downloadLink:add',    '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('下载链接修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:downloadLink:edit',   '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('下载链接删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:downloadLink:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('下载链接导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'xmbj:downloadLink:export', '#', 'admin', sysdate(), '', null, '');
