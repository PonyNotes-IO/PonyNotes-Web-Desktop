FROM nginx:alpine

# 复制自定义Nginx配置
COPY ./nginx.conf /etc/nginx/nginx.conf
# 复制前端静态资源到Nginx默认路径
# COPY ./web/* /opt/www/
COPY ./web/ /opt/www/

EXPOSE 8085
EXPOSE 8090
EXPOSE 8095