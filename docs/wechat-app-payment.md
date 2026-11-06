# Android 微信 App 支付

新增 `paymentType=wechat_app`，仅服务 Android App。原有 `wechat`、`wechat_jsapi`、`wechat_native` 的下单、网页 AppID、通知地址和处理逻辑保留。

## 上线前

1. 在会员 PostgreSQL 数据库（SLAVE 数据源）执行 `sql/wechat_app_payment.sql`。此表与会员开通同事务提交，确保并发通知、查单和跨库写入失败重试不会重复续期。缺表时会在下单前报错。
2. 保留现有 `payment.wechat.app-id`、商户号、证书、API v3 密钥和 `notify-url`；增加独立配置：

   ```yaml
   payment:
     wechat:
       app-pay-app-id: wx3b1a7737f52a004b
       app-notify-url: https://支付域名/prod-api/api/payment/callback/wechat/app
   ```

   默认 AppID 为上述值。未显式填写 App 通知地址时，只有旧地址以 `/callback/wechat` 结尾才会派生为旧地址加 `/app`。网关需将新路径转发到 Java 服务，允许微信直接通知。
3. 微信商户平台须开通 App 支付，并将该移动应用 AppID 与现有商户号关联。微信开放平台的 Android 包名、签名需与实际 APK 一致；前端不需要商户私钥、API v3 密钥或 AppSecret。
4. 核对现有 `ruoyi.payment-debug`：项目默认 `Y` 会按 **0.01元** 下单，生产环境应设为 `N`。此次保留原定价开关。
5. 先部署数据库迁移及支付后端，再发布 Android 客户端。

## 接口

- 下单仍为 `POST /api/payment/create`，表单传 `paymentType=wechat_app`、`userInfo`、`planId`、`billingType=0/1`（月/年）；App 支付不需要 openid，当前不支持附加包。
- `data.payUrl` 为 JSON 字符串，包含 SDK 参数 `appid/partnerid/prepayid/package/noncestr/timestamp/sign`，不是网页或二维码 URL。
- 新通知入口为 `POST /api/payment/callback/wechat/app`，官方 SDK 验签解密后校验商户号、AppID、交易类型、订单号及人民币金额。成功返回 HTTP 204，失败返回 500 供微信重试。
- 查单仍为 `GET /api/payment/status?orderNo=...`；只有 `wechat_app` 订单进入新增的 App 查单/会员开通分支。客户端只有收到后端 `success` 才展示成功。

## 本地验证

```powershell
./script/test_wechat_app_payment.ps1 -Maven 'E:\fly\FlyEnv-Data\env\maven\bin\mvn.cmd'
```

使用临时 RSA 密钥和加密通知，不访问支付平台或数据库。检查官方 SDK 验签解密、篡改签名、错误金额/AppID、重复通知、模拟跨库失败重试及旧回调隔离。数据库迁移、并发事务和实际微信拉起需在测试环境联调；该脚本不代替真机验收。

传入 `-LocalPostgres` 可额外检查 `mydev` 指向的本地 PostgreSQL：调用真实会员 Mapper 插入一条记录，确认数据库生成的 ID 已回填并在回滚后确认记录不存在。检查要求已有测试订阅，仅接受 `localhost:5432/xiaomabiji`。默认不访问数据库。

前端验证：在 Flutter 目录运行 `flutter test --no-pub test/unit_test/payment/wechat_app_payment_test.dart test/widget_test/mobile_payment_method_selector_test.dart`。
