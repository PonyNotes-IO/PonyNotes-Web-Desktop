-- App类型字典（已存在，无需创建）
-- dict_type: sys_app_type
-- 数据已存在，包含：Andriod(0), ios(1), iPad(2), Pad(3), mac(4), win(5)

-- App版本类型字典（需要创建）
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('App版本类型', 'sys_app_version', '0', 'admin', sysdate(), 'App版本类型列表');

-- App版本类型字典数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time, remark)
VALUES 
(1, '正式版本', '1', 'sys_app_version', '0', 'admin', sysdate(), 'App版本类型'),
(2, '测试版本', '2', 'sys_app_version', '0', 'admin', sysdate(), 'App版本类型'),
(3, '灰度版本', '3', 'sys_app_version', '0', 'admin', sysdate(), 'App版本类型');

-- App状态字典（需要创建）
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('App状态', 'sys_app_status', '0', 'admin', sysdate(), 'App状态列表');

-- App状态字典数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time, remark)
VALUES 
(1, '未发布', '0', 'sys_app_status', '0', 'admin', sysdate(), 'App状态'),
(2, '已发布', '1', 'sys_app_status', '0', 'admin', sysdate(), 'App状态'),
(3, '已停用', '2', 'sys_app_status', '0', 'admin', sysdate(), 'App状态');
