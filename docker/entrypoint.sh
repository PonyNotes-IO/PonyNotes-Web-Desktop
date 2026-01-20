#!/bin/sh

# 启动Nginx
nginx &

# 启动Java后端
cd /app && java -jar app.jar --spring.profiles.active=druid --aliyun.sms.access-key-id=${ALIYUN_SMS_ACCESS_KEY_ID:-} --aliyun.sms.access-key-secret=${ALIYUN_SMS_ACCESS_KEY_SECRET:-}

# 等待后台进程结束（实际不会执行到这里，因为Java会持续运行）
wait