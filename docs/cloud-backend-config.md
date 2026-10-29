# Cloud 后端地址

Java 后端的订阅、短信 Feign 客户端共用 `ruoyi-xmbj-api` 中的 `Urls.API_HOST`。
未配置时继续使用 `https://api.xiaomabiji.com`。

测试服 Docker Compose 设置：

```dotenv
PONYNOTES_CLOUD_URL=http://appflowy_cloud:8000
```

也可以在 Spring 配置中设置 `ponynotes.cloud-url`。地址填 Cloud 根地址，不附加 `/api`。

一键部署脚本位于 Cloud 仓库 `deploy/47.94.42.183/deploy-test.py`，使用说明见同目录 README。
