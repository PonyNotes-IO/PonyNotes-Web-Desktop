-- 删除所有 AI 模型相关的菜单
DELETE FROM sys_menu WHERE menu_name LIKE '%AI模型%' OR menu_name LIKE '%aiModel%' OR component LIKE '%aiModel%';

-- 删除角色菜单关联
DELETE FROM sys_role_menu WHERE menu_id NOT IN (SELECT menu_id FROM sys_menu);

-- 插入新的 AI 模型管理菜单（path 改为 models）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('AI模型管理', '1', '4', 'models', 'system/models/index', 1, 0, 'C', '0', '0', 'system:models:list', '#', 'admin', sysdate(), '', null, 'AI模型管理菜单');

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

-- 为管理员角色分配 AI 模型管理菜单权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE menu_name LIKE 'AI模型%'
ON DUPLICATE KEY UPDATE role_id = role_id;
