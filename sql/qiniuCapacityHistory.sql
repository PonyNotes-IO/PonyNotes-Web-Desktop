-- 七牛云容量历史记录表
drop table if exists qiniu_capacity_history;
create table qiniu_capacity_history (
  history_id         bigint(20)     not null auto_increment    comment '历史记录ID',
  record_date        date            not null                   comment '记录日期',
  capacity_bytes     bigint(20)     default 0                 comment '容量（字节）',
  capacity_tb       decimal(10, 2) default 0.00           comment '容量（TB）',
  file_count        bigint(20)     default 0                 comment '文件数量',
  usage_rate        decimal(5, 2)  default 0.00           comment '使用率（%）',
  create_time       datetime                                   comment '创建时间',
  primary key (history_id),
  unique key uk_record_date (record_date)
) engine=innodb auto_increment=1 comment = '七牛云容量历史记录表';

-- 插入示例数据（实际使用时由定时任务自动插入）
insert into qiniu_capacity_history (record_date, capacity_bytes, capacity_tb, file_count, usage_rate, create_time)
values 
('2025-07-31', 771061232679936, 701.00, 0, 4.18, '2025-07-31 23:59:59'),
('2025-08-31', 824633820856320, 750.00, 0, 4.47, '2025-08-31 23:59:59'),
('2025-09-30', 879609302220800, 801.00, 0, 4.78, '2025-09-30 23:59:59'),
('2025-10-31', 922746882435072, 840.00, 0, 5.01, '2025-10-31 23:59:59'),
('2025-11-30', 956831554695168, 871.00, 0, 5.19, '2025-11-30 23:59:59'),
('2025-12-31', 983570446868480, 895.00, 0, 5.33, '2025-12-31 23:59:59');
