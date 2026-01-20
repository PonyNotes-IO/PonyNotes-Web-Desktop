SELECT dict_type, dict_name, dict_label, dict_value, dict_sort, status, remark
FROM sys_dict_data
WHERE dict_type IN ('sys_yes_no', 'sys_has_api_support', 'sys_app_type', 'sys_app_status')
ORDER BY dict_type, dict_sort;
