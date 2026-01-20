-- App类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('App类型', 'sys_app_type', '0', 'admin', sysdate(), 'App类型列表');

-- App类型字典数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time, remark)
VALUES 
(1, 'iOS', 'ios', 'sys_app_type', '0', 'admin', sysdate(), 'App类型'),
(2, 'Android', 'android', 'sys_app_type', '0', 'admin', sysdate(), 'App类型'),
(3, 'Web', 'web', 'sys_app_type', '0', 'admin', sysdate(), 'App类型');

-- App状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('App状态', 'sys_app_status', '0', 'admin', sysdate(), 'App状态列表');

-- App状态字典数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time, remark)
VALUES 
(1, '未发布', '0', 'sys_app_status', '0', 'admin', sysdate(), 'App状态'),
(2, '已发布', '1', 'sys_app_status', '0', 'admin', sysdate(), 'App状态'),
(3, '已停用', '2', 'sys_app_status', '0', 'admin', sysdate(), 'App状态');
