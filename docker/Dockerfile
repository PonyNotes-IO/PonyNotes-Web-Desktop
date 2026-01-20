
#FROM nginx:alpine
#RUN apk add --no-cache openjdk8-jre
ARG SMS_ACCESS_KEY_SECRET=10
ARG SMS_ACCESS_KEY_ID=aa

FROM xm-server:1.0

ENV ALIYUN_SMS_ACCESS_KEY_SECRET=${SMS_ACCESS_KEY_SECRET:-}
ENV ALIYUN_SMS_ACCESS_KEY_ID=${SMS_ACCESS_KEY_ID:-}

# 复制前端静态资源到Nginx默认路径
COPY ./nginx/manager /usr/opt/
COPY ./nginx/www /usr/opt/

# 复制后端JAR包
COPY ./server/target/*.jar /app/app.jar

# 复制自定义Nginx配置
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf

# 启动脚本（关键：同时启动Nginx和Java）
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8085
EXPOSE 8090

ENTRYPOINT ["/entrypoint.sh"]