-- 字典管理父菜单
insert into sys_menu values('105',  '字典管理', '1',   '6', 'dict',       '',                          '', '', 1, 0, 'M', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, '字典管理目录');

-- 字典类型管理子菜单
insert into sys_menu values('2001', '字典类型管理', '105', '1', 'type',      'system/dict/index',        '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'table',         'admin', sysdate(), '', null, '字典类型管理菜单');

-- 字典数据管理子菜单
insert into sys_menu values('2002', '字典数据管理', '105', '2', 'data',      'system/dict/dataManage',   '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'edit',          'admin', sysdate(), '', null, '字典数据管理菜单');

-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '105', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', '字典新增', '105', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '字典修改', '105', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '字典删除', '105', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '字典导出', '105', '7', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
