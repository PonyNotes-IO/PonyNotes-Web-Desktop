-- 应用版本状态字典
insert into sys_dict_type values(100, '应用版本状态', 'sys_app_status', '0', 'admin', sysdate(), '', null, '应用版本状态列表');

-- 应用版本状态字典数据
insert into sys_dict_data values(1000, 1,  '未发布',    '0',       'sys_app_status',   '',   'info',    'N', '0', 'admin', sysdate(), '', null, '未发布状态');
insert into sys_dict_data values(1001, 2,  '已发布',    '1',       'sys_app_status',   '',   'success', 'N', '0', 'admin', sysdate(), '', null, '已发布状态');
insert into sys_dict_data values(1002, 3,  '已停用',    '2',       'sys_app_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '已停用状态');
