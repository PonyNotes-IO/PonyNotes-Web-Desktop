# Gotrue和Cloud数据库PostgreSQL主要数据表结构说明文档

## 文档说明

本文档详细说明了PonyNotes项目中GoTrue认证服务和AppFlowy Cloud服务使用的PostgreSQL数据库主要表结构。

**数据库连接信息：**
- 服务器地址：8.152.101.166
- 数据库名：xiaomabiji
- Schema：`auth`（GoTrue相关表）、`public`（Cloud相关表）

---

## 一、GoTrue认证服务表结构（auth schema）

GoTrue是用于用户认证和授权的服务，所有表都在`auth` schema下。

### 1.1 users - 用户表

存储用户登录和认证信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| instance_id | uuid | 实例ID | NULL |
| id | uuid | 用户唯一标识 | PRIMARY KEY, UNIQUE, NOT NULL |
| aud | varchar(255) | 受众标识 | NULL |
| role | varchar(255) | 用户角色 | NULL |
| email | varchar(255) | 邮箱地址 | UNIQUE, NULL |
| encrypted_password | varchar(255) | 加密后的密码 | NULL |
| confirmed_at | timestamptz | 确认时间 | NULL |
| invited_at | timestamptz | 邀请时间 | NULL |
| confirmation_token | varchar(255) | 确认令牌 | NULL |
| confirmation_sent_at | timestamptz | 确认邮件发送时间 | NULL |
| recovery_token | varchar(255) | 恢复令牌 | NULL |
| recovery_sent_at | timestamptz | 恢复邮件发送时间 | NULL |
| email_change_token | varchar(255) | 邮箱变更令牌 | NULL |
| email_change | varchar(255) | 新邮箱地址 | NULL |
| email_change_sent_at | timestamptz | 邮箱变更邮件发送时间 | NULL |
| last_sign_in_at | timestamptz | 最后登录时间 | NULL |
| raw_app_meta_data | jsonb | 应用元数据（JSON） | NULL |
| raw_user_meta_data | jsonb | 用户元数据（JSON） | NULL |
| is_super_admin | bool | 是否超级管理员 | NULL |
| phone | varchar(255) | 手机号 | NULL |
| phone_confirmed_at | timestamptz | 手机号确认时间 | NULL |
| email_confirmed_at | timestamptz | 邮箱确认时间 | NULL |
| is_sso_user | bool | 是否SSO用户 | NULL |
| is_anonymous | bool | 是否匿名用户 | NULL |
| password_is_set | bool | 密码是否已设置 | NULL |
| created_at | timestamptz | 创建时间 | NULL |
| updated_at | timestamptz | 更新时间 | NULL |

**索引：**
- `users_instance_id_email_idx` (instance_id, email)
- `users_instance_id_idx` (instance_id)

**说明：**
- 这是GoTrue的核心用户表，存储所有用户的认证信息
- `id`字段对应Cloud服务中`af_user.uuid`字段
- `encrypted_password`存储bcrypt加密后的密码
- `raw_user_meta_data`可以存储用户的自定义元数据

---

### 1.2 refresh_tokens - 刷新令牌表

存储用于刷新JWT令牌的刷新令牌。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| instance_id | uuid | 实例ID | NULL |
| id | bigserial | 主键ID | PRIMARY KEY |
| token | varchar(255) | 刷新令牌 | NULL |
| user_id | varchar(255) | 用户ID | NULL |
| session_id | uuid | 会话ID | NULL, FK -> sessions(id) |
| revoked | bool | 是否已撤销 | NULL |
| parent | varchar(255) | 父令牌 | NULL |
| created_at | timestamptz | 创建时间 | NULL |
| updated_at | timestamptz | 更新时间 | NULL |

**索引：**
- `refresh_tokens_instance_id_idx` (instance_id)
- `refresh_tokens_instance_id_user_id_idx` (instance_id, user_id)
- `refresh_tokens_token_idx` (token)

**说明：**
- 用于实现JWT令牌的刷新机制
- `revoked`字段标记令牌是否已被撤销
- `session_id`关联到sessions表

---

### 1.3 sessions - 会话表

存储用户会话信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 会话ID | PRIMARY KEY |
| user_id | uuid | 用户ID | NOT NULL, FK -> users(id) |
| created_at | timestamptz | 创建时间 | NULL |
| updated_at | timestamptz | 更新时间 | NULL |
| factor_id | uuid | MFA因子ID | NULL |
| aal | aal_level | 认证保证级别 | NULL |
| not_after | timestamptz | 过期时间 | NULL |
| tag | text | 标签 | NULL |
| ip | inet | IP地址 | NULL |
| user_agent | text | 用户代理 | NULL |

**说明：**
- 管理用户登录会话
- 支持多因素认证（MFA）
- `aal`字段表示认证保证级别（aal1/aal2/aal3）

---

### 1.4 identities - 身份表

存储用户的不同身份提供者（如邮箱、手机、第三方登录等）。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | text | 身份ID | PRIMARY KEY (provider, id) |
| user_id | uuid | 用户ID | NOT NULL, FK -> users(id) |
| identity_data | jsonb | 身份数据（JSON） | NOT NULL |
| provider | text | 提供者类型 | PRIMARY KEY (provider, id), NOT NULL |
| last_sign_in_at | timestamptz | 最后登录时间 | NULL |
| email | varchar(255) | 邮箱（用于邮箱身份） | NULL |
| created_at | timestamptz | 创建时间 | NULL |
| updated_at | timestamptz | 更新时间 | NULL |

**索引：**
- `identities_user_id_idx` (user_id)

**说明：**
- 一个用户可以有多个身份（邮箱、手机、Google、GitHub等）
- `provider`字段标识身份提供者类型（email、phone、google等）
- `identity_data`存储提供者返回的原始数据

---

### 1.5 mfa_factors - 多因素认证因子表

存储用户的MFA认证因子（如TOTP、WebAuthn等）。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 因子ID | PRIMARY KEY |
| user_id | uuid | 用户ID | NOT NULL, FK -> users(id) |
| friendly_name | text | 友好名称 | NULL |
| factor_type | factor_type | 因子类型（totp/webauthn） | NOT NULL |
| status | factor_status | 状态（unverified/verified） | NOT NULL |
| secret | text | 密钥（TOTP使用） | NULL |
| last_challenged_at | timestamptz | 最后挑战时间 | NULL |
| created_at | timestamptz | 创建时间 | NOT NULL |
| updated_at | timestamptz | 更新时间 | NOT NULL |

**索引：**
- `mfa_factors_user_friendly_name_unique` (friendly_name, user_id) UNIQUE

**说明：**
- 支持TOTP（时间-based一次性密码）和WebAuthn（Web认证API）
- `status`字段标识因子是否已验证

---

### 1.6 mfa_challenges - MFA挑战表

存储MFA挑战请求。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 挑战ID | PRIMARY KEY |
| factor_id | uuid | 因子ID | NOT NULL, FK -> mfa_factors(id) |
| created_at | timestamptz | 创建时间 | NOT NULL |
| verified_at | timestamptz | 验证时间 | NULL |
| ip_address | inet | IP地址 | NOT NULL |

**说明：**
- 记录每次MFA验证挑战
- `verified_at`为NULL表示未验证，有值表示已验证

---

### 1.7 mfa_amr_claims - MFA认证方法引用声明表

存储会话的认证方法引用（AMR）声明。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| session_id | uuid | 会话ID | PRIMARY KEY (session_id, authentication_method), FK -> sessions(id) |
| created_at | timestamptz | 创建时间 | NOT NULL |
| updated_at | timestamptz | 更新时间 | NOT NULL |
| authentication_method | text | 认证方法 | PRIMARY KEY (session_id, authentication_method), NOT NULL |

**说明：**
- 记录会话使用的认证方法（如password、totp、webauthn等）
- 用于实现认证保证级别（AAL）

---

### 1.8 audit_log_entries - 审计日志表

存储用户操作的审计日志。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| instance_id | uuid | 实例ID | NULL |
| id | uuid | 日志ID | PRIMARY KEY |
| payload | json | 日志内容（JSON） | NULL |
| ip_address | inet | IP地址 | NULL |
| created_at | timestamptz | 创建时间 | NULL |

**索引：**
- `audit_logs_instance_id_idx` (instance_id)

**说明：**
- 记录所有重要的用户操作（登录、注册、密码重置等）
- `payload`字段存储操作的详细信息

---

### 1.9 instances - 实例表

管理多站点实例配置。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 实例ID | PRIMARY KEY |
| uuid | uuid | UUID | NULL |
| raw_base_config | text | 基础配置（文本） | NULL |
| created_at | timestamptz | 创建时间 | NULL |
| updated_at | timestamptz | 更新时间 | NULL |

**说明：**
- 用于管理多个站点的用户认证
- 每个实例可以有独立的配置

---

### 1.10 sign_in_logs - 登录日志表

记录每次登录尝试的详细信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 日志ID | PRIMARY KEY |
| user_uuid | uuid | 用户UUID | NULL |
| user_uid | bigint | 用户UID（对应af_user.uid） | NULL |
| provider | text | 登录提供者 | NULL |
| third_party_id | text | 第三方ID | NULL |
| ip_address | inet | IP地址 | NULL |
| country | text | 国家 | NULL |
| region | text | 地区 | NULL |
| city | text | 城市 | NULL |
| user_agent | text | 用户代理 | NULL |
| success | boolean | 是否成功 | NOT NULL, DEFAULT true |
| error_reason | text | 错误原因 | NULL |
| metadata | jsonb | 元数据（JSON） | DEFAULT '{}' |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `sign_in_logs_user_uuid_idx` (user_uuid, created_at DESC)
- `sign_in_logs_user_uid_idx` (user_uid, created_at DESC)
- `sign_in_logs_created_at_idx` (created_at)

**说明：**
- 记录每次登录尝试的详细信息，包括地理位置、IP地址等
- `success`字段标识登录是否成功
- `user_uid`字段关联到Cloud服务的`af_user.uid`

---

### 1.11 flow_state - 流程状态表

存储PKCE（Proof Key for Code Exchange）登录流程状态。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 状态ID | PRIMARY KEY |
| user_id | uuid | 用户ID | NULL |
| auth_code | text | 授权码 | NOT NULL |
| code_challenge_method | code_challenge_method | 挑战方法（s256/plain） | NOT NULL |
| code_challenge | text | 代码挑战 | NOT NULL |
| provider_type | text | 提供者类型 | NOT NULL |
| provider_access_token | text | 提供者访问令牌 | NULL |
| provider_refresh_token | text | 提供者刷新令牌 | NULL |
| authentication_method | text | 认证方法 | NULL |
| relay_state | text | 中继状态 | NULL |
| issued_at | timestamptz | 签发时间 | NULL |
| created_at | timestamptz | 创建时间 | NULL |
| updated_at | timestamptz | 更新时间 | NULL |

**索引：**
- `idx_auth_code` (auth_code)

**说明：**
- 用于OAuth/OIDC流程中的PKCE验证
- 存储临时授权码和挑战值

---

### 1.12 one_time_tokens - 一次性令牌表

存储各种一次性令牌（确认、恢复、邮箱变更等）。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 令牌ID | PRIMARY KEY |
| user_id | uuid | 用户ID | NOT NULL, FK -> users(id) |
| token_type | one_time_token_type | 令牌类型 | NOT NULL |
| token_hash | text | 令牌哈希 | NOT NULL |
| relates_to | text | 关联对象 | NOT NULL |
| created_at | timestamp | 创建时间 | NOT NULL, DEFAULT now() |
| updated_at | timestamp | 更新时间 | NOT NULL, DEFAULT now() |

**索引：**
- `one_time_tokens_token_hash_hash_idx` (token_hash) USING hash
- `one_time_tokens_relates_to_hash_idx` (relates_to) USING hash
- `one_time_tokens_user_id_token_type_key` (user_id, token_type) UNIQUE

**令牌类型枚举：**
- `confirmation_token` - 确认令牌
- `reauthentication_token` - 重新认证令牌
- `recovery_token` - 恢复令牌
- `email_change_token_new` - 新邮箱变更令牌
- `email_change_token_current` - 当前邮箱变更令牌
- `phone_change_token` - 手机号变更令牌

**说明：**
- 所有一次性令牌都存储哈希值，不存储明文
- 每个用户每种类型的令牌只能有一个

---

### 1.13 oauth_clients - OAuth客户端表

存储OAuth客户端应用信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 客户端ID | PRIMARY KEY |
| client_id | text | 客户端标识 | UNIQUE, NOT NULL |
| client_secret_hash | text | 客户端密钥哈希 | NOT NULL |
| registration_type | oauth_registration_type | 注册类型（dynamic/manual） | NOT NULL |
| redirect_uris | text | 重定向URI列表 | NOT NULL |
| grant_types | text | 授权类型列表 | NOT NULL |
| client_name | text | 客户端名称 | NULL, <= 1024 |
| client_uri | text | 客户端URI | NULL, <= 2048 |
| logo_uri | text | Logo URI | NULL, <= 2048 |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT now() |
| updated_at | timestamptz | 更新时间 | NOT NULL, DEFAULT now() |
| deleted_at | timestamptz | 删除时间 | NULL |

**索引：**
- `oauth_clients_client_id_idx` (client_id)
- `oauth_clients_deleted_at_idx` (deleted_at)

**说明：**
- 管理OAuth 2.0客户端应用
- 支持动态注册和手动注册两种方式

---

### 1.14 schema_migrations - 迁移记录表

记录数据库迁移版本。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| version | varchar(255) | 迁移版本号 | PRIMARY KEY |

**说明：**
- 用于管理数据库迁移
- 记录已执行的迁移脚本版本

---

## 二、AppFlowy Cloud服务表结构（public schema）

Cloud服务表都在`public` schema下，以`af_`前缀命名。

### 2.1 af_user - 用户表

存储Cloud服务的用户信息，与GoTrue的`auth.users`表通过`uuid`字段关联。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| uid | bigint | 用户ID（主键） | PRIMARY KEY |
| uuid | uuid | GoTrue用户UUID | NOT NULL, FK -> auth.users(id) |
| email | text | 邮箱地址 | UNIQUE, DEFAULT '' |
| password | text | 密码（已废弃，使用GoTrue认证） | DEFAULT '' |
| phone | text | 手机号 | NULL |
| name | text | 用户名 | NOT NULL, DEFAULT '' |
| metadata | jsonb | 用户元数据（头像、OpenAI密钥等） | DEFAULT '{}' |
| encryption_sign | text | 数据加密签名 | NULL |
| deleted_at | timestamptz | 删除时间 | NULL |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**触发器：**
- `update_af_user_modtime` - 自动更新`updated_at`字段
- `trigger_prevent_reset_encryption_sign` - 防止重置`encryption_sign`字段

**说明：**
- `uid`是Cloud服务的用户主键，自增bigint
- `uuid`关联到GoTrue的`auth.users.id`
- `encryption_sign`用于加密用户数据，一旦设置不能重置
- `metadata`字段存储JSON格式的用户元数据（如头像URL、OpenAI API密钥等）

---

### 2.2 af_workspace - 工作空间表

存储用户的工作空间信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| workspace_id | uuid | 工作空间ID | PRIMARY KEY, DEFAULT uuid_generate_v4() |
| database_storage_id | uuid | 数据库存储ID | NOT NULL, DEFAULT uuid_generate_v4() |
| owner_uid | bigint | 所有者用户ID | NOT NULL, FK -> af_user(uid) |
| workspace_name | text | 工作空间名称 | DEFAULT 'My Workspace' |
| workspace_type | integer | 工作空间类型（0=免费） | NOT NULL, DEFAULT 0 |
| publish_namespace | text | 发布命名空间 | UNIQUE |
| deleted_at | timestamptz | 删除时间 | NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `publish_namespace_idx` (publish_namespace)

**说明：**
- 每个工作空间属于一个用户（owner_uid）
- `workspace_type`：0=免费版
- `publish_namespace`用于发布视图的URL前缀

---

### 2.3 af_workspace_member - 工作空间成员表

存储工作空间的成员及其角色。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| uid | bigint | 用户ID | PRIMARY KEY, FK -> af_user(uid) |
| workspace_id | uuid | 工作空间ID | PRIMARY KEY, FK -> af_workspace(workspace_id) |
| role_id | int | 角色ID | NOT NULL, FK -> af_roles(id) |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `idx_af_workspace_member` (uid, workspace_id, role_id) UNIQUE

**触发器：**
- `notify_af_workspace_member_change` - 通过pg_notify通知成员变更

**说明：**
- 记录工作空间的成员及其角色
- 主键为(uid, workspace_id)
- 通过触发器实现实时通知功能

---

### 2.4 af_workspace_member_profile - 工作空间成员资料表

存储工作空间成员的详细资料。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| uid | bigint | 用户ID | PRIMARY KEY, FK -> af_user(uid) |
| workspace_id | uuid | 工作空间ID | PRIMARY KEY, FK -> af_workspace(workspace_id) |
| name | text | 成员名称 | NULL |
| avatar_url | text | 头像URL | NULL |
| custom_image_url | text | 自定义图片URL | NULL |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储成员在工作空间中的个性化资料
- 允许成员在不同工作空间中有不同的名称和头像

---

### 2.5 af_roles - 角色表

定义系统角色。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 角色ID | PRIMARY KEY |
| name | text | 角色名称 | UNIQUE, NOT NULL |

**默认角色：**
- Owner（所有者）
- Member（成员）
- Guest（访客）

---

### 2.6 af_permissions - 权限表

定义系统权限。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 权限ID | PRIMARY KEY |
| name | varchar(255) | 权限名称 | UNIQUE, NOT NULL |
| access_level | integer | 访问级别 | NOT NULL |
| description | text | 权限描述 | NULL |

**默认权限：**
- Read only（只读）- access_level: 10
- Read and comment（读和评论）- access_level: 20
- Read and write（读写）- access_level: 30
- Full access（完全访问）- access_level: 50

---

### 2.7 af_role_permissions - 角色权限关联表

定义角色拥有的权限。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| role_id | int | 角色ID | PRIMARY KEY, FK -> af_roles(id) |
| permission_id | int | 权限ID | PRIMARY KEY, FK -> af_permissions(id) |

**默认关联：**
- Owner -> Full access
- Member -> Read and write
- Guest -> Read only

---

### 2.8 af_collab - 协作对象表

存储协作对象（文档、数据库、文件夹等）的数据，使用分区表。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| oid | text | 对象ID | PRIMARY KEY |
| blob | bytea | 对象数据（二进制） | NOT NULL |
| len | integer | 数据长度 | NULL |
| partition_key | integer | 分区键 | PRIMARY KEY, NOT NULL |
| encrypt | integer | 是否加密（0=否） | DEFAULT 0 |
| owner_uid | bigint | 所有者用户ID | NOT NULL |
| workspace_id | uuid | 工作空间ID | NOT NULL, FK -> af_workspace(workspace_id) |
| deleted_at | timestamptz | 删除时间 | NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | NULL |
| indexed_at | timestamptz | 索引时间 | NULL |

**分区表：**
- `af_collab_document` - partition_key = 0（文档）
- `af_collab_database` - partition_key = 1（数据库）
- `af_collab_w_database` - partition_key = 2（工作空间数据库）
- `af_collab_folder` - partition_key = 3（文件夹）
- `af_collab_database_row` - partition_key = 4（数据库行）
- `af_collab_user_awareness` - partition_key = 5（用户感知）

**说明：**
- 使用分区表提高查询性能
- `blob`字段存储CRDT格式的协作数据
- `partition_key`用于区分不同类型的协作对象

---

### 2.9 af_collab_member - 协作对象成员表

存储协作对象的成员及其权限。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| uid | bigint | 用户ID | PRIMARY KEY, FK -> af_user(uid) |
| oid | text | 对象ID | PRIMARY KEY |
| permission_id | integer | 权限ID | NOT NULL, FK -> af_permissions(id) |

**触发器：**
- `notify_af_collab_member_change` - 通过pg_notify通知成员变更

**说明：**
- 控制用户对特定协作对象的访问权限
- 主键为(uid, oid)

---

### 2.10 af_collab_member_invite - 协作对象成员邀请表

存储协作对象的成员邀请信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 邀请ID | PRIMARY KEY |
| oid | text | 对象ID | NOT NULL |
| inviter_uid | bigint | 邀请者用户ID | NOT NULL |
| invitee_email | text | 被邀请者邮箱 | NOT NULL |
| permission_id | integer | 权限ID | NOT NULL |
| status | text | 状态 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 用于邀请用户加入协作对象
- `status`字段标识邀请状态（pending/accepted/rejected等）

---

### 2.11 af_collab_snapshot - 协作对象快照表

存储协作对象的历史快照。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| sid | bigserial | 快照ID | PRIMARY KEY |
| oid | text | 对象ID | NOT NULL |
| blob | bytea | 快照数据（二进制） | NOT NULL |
| len | integer | 数据长度 | NOT NULL |
| encrypt | integer | 是否加密（0=否） | DEFAULT 0 |
| workspace_id | uuid | 工作空间ID | NOT NULL, FK -> af_workspace(workspace_id) |
| deleted_at | timestamptz | 删除时间 | NULL |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `idx_af_collab_snapshot_oid` (oid)

**说明：**
- 用于版本历史和恢复功能
- 定期创建协作对象的快照

---

### 2.12 af_collab_embeddings - 协作对象嵌入向量表

存储协作对象的文本嵌入向量，用于AI搜索。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| fragment_id | text | 片段ID | PRIMARY KEY |
| oid | text | 对象ID | NOT NULL |
| partition_key | integer | 分区键 | NOT NULL |
| content_type | integer | 内容类型 | NOT NULL |
| content | text | 文本内容 | NULL |
| embedding | vector(1536) | 嵌入向量（1536维） | NULL |
| indexed_at | timestamp | 索引时间 | NOT NULL, DEFAULT NOW() |
| metadata | jsonb | 元数据（JSON） | NULL |

**索引：**
- `af_collab_embeddings_similarity_idx` (embedding) USING hnsw - 向量相似度索引

**外键：**
- (oid, partition_key) -> af_collab(oid, partition_key)

**说明：**
- 使用pgvector扩展存储向量数据
- 用于语义搜索和AI功能
- 使用HNSW索引提高相似度搜索性能

---

### 2.13 af_snapshot_meta - 快照元数据表（分区表）

存储快照的元数据，使用分区表。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| oid | text | 对象ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL, FK -> af_workspace(workspace_id) |
| snapshot | bytea | 快照数据 | NOT NULL |
| snapshot_version | integer | 快照版本 | NOT NULL |
| partition_key | integer | 分区键 | PRIMARY KEY, NOT NULL |
| created_at | bigint | 创建时间（时间戳） | NOT NULL |
| metadata | jsonb | 元数据（JSON） | NULL |

**分区表：**
- `af_snapshot_meta_document` - partition_key = 0
- `af_snapshot_meta_database` - partition_key = 1
- `af_snapshot_meta_workspace_database` - partition_key = 2
- `af_snapshot_meta_folder` - partition_key = 3
- `af_snapshot_meta_database_row` - partition_key = 4
- `af_snapshot_meta_user_awareness` - partition_key = 5

---

### 2.14 af_snapshot_state - 快照状态表（分区表）

存储快照的状态数据，使用分区表。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| snapshot_id | uuid | 快照ID | PRIMARY KEY, DEFAULT uuid_generate_v4() |
| workspace_id | uuid | 工作空间ID | NOT NULL, FK -> af_workspace(workspace_id) |
| oid | text | 对象ID | NOT NULL |
| doc_state | bytea | 文档状态（二进制） | NOT NULL |
| doc_state_version | integer | 文档状态版本 | NOT NULL |
| deps_snapshot_id | uuid | 依赖快照ID | NULL |
| partition_key | integer | 分区键 | PRIMARY KEY, NOT NULL |
| created_at | bigint | 创建时间（时间戳） | NOT NULL |

**分区表：**
- `af_snapshot_state_document` - partition_key = 0
- `af_snapshot_state_database` - partition_key = 1
- `af_snapshot_state_workspace_database` - partition_key = 2
- `af_snapshot_state_folder` - partition_key = 3
- `af_snapshot_state_database_row` - partition_key = 4
- `af_snapshot_state_user_awareness` - partition_key = 5

**索引：**
- `idx_snapshot_state_oid_created` (oid, created_at DESC)

---

### 2.15 af_blob_metadata - 文件元数据表

存储文件的元数据信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| workspace_id | uuid | 工作空间ID | NOT NULL, FK -> af_workspace(workspace_id) |
| file_id | varchar | 文件ID | NOT NULL |
| file_type | varchar | 文件类型 | NOT NULL |
| file_size | bigint | 文件大小（字节） | NOT NULL |
| file_status | varchar | 文件状态 | NULL |
| file_source | varchar | 文件来源 | NULL |
| modified_at | timestamptz | 修改时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

**唯一约束：**
- (workspace_id, file_id) UNIQUE

**说明：**
- 存储文件的元数据，实际文件内容存储在对象存储中
- `file_id`是文件的唯一标识

---

### 2.16 af_chat - 聊天表

存储AI聊天会话。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| chat_id | uuid | 聊天ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL, FK -> af_workspace(workspace_id) |
| name | text | 聊天名称 | NOT NULL, DEFAULT '' |
| rag_ids | jsonb | RAG ID列表 | NOT NULL, DEFAULT '[]' |
| deleted_at | timestamptz | 删除时间 | NULL |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储AI聊天会话信息
- `rag_ids`存储关联的RAG（检索增强生成）文档ID列表

---

### 2.17 af_chat_messages - 聊天消息表

存储AI聊天的消息记录。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| message_id | bigserial | 消息ID | PRIMARY KEY |
| chat_id | uuid | 聊天ID | NOT NULL, FK -> af_chat(chat_id) |
| author | jsonb | 作者信息（JSON） | NOT NULL |
| content | text | 消息内容 | NOT NULL |
| deleted_at | timestamptz | 删除时间 | NULL |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| edited_at | timestamp | 编辑时间 | NULL |

**索引：**
- `idx_chat_messages_chat_id_created_at` (message_id ASC, created_at ASC)

**说明：**
- 存储聊天消息内容
- `author`字段存储JSON格式的作者信息（用户ID、类型等）

---

### 2.18 af_chat_message_meta - 聊天消息元数据表

存储聊天消息的元数据。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| message_id | bigint | 消息ID | PRIMARY KEY, FK -> af_chat_messages(message_id) |
| metadata | jsonb | 元数据（JSON） | NULL |

**说明：**
- 存储消息的额外元数据（如token使用量、模型信息等）

---

### 2.19 af_workspace_invitation - 工作空间邀请表

存储工作空间邀请信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 邀请ID | PRIMARY KEY, DEFAULT uuid_generate_v4() |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| inviter | bigint | 邀请者用户ID | NOT NULL |
| invitee | bigint | 被邀请者用户ID | NOT NULL |
| role_id | int | 角色ID | NOT NULL |
| status | smallint | 状态（0=待处理，1=已接受，2=已拒绝） | NOT NULL, DEFAULT 0 |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `idx_af_workspace_invitation_inviter` (inviter)
- `idx_af_workspace_invitation_invitee` (invitee)

**触发器：**
- `af_workspace_invitation_status_update` - 状态变更时更新updated_at
- `af_workspace_invitation_accepted` - 接受邀请时自动添加到af_workspace_member

**说明：**
- 管理工作空间邀请流程
- 接受邀请后自动将用户添加到工作空间成员表

---

### 2.20 af_workspace_invite_code - 工作空间邀请码表

存储工作空间的邀请码。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| workspace_id | uuid | 工作空间ID | PRIMARY KEY, FK -> af_workspace(workspace_id) |
| invite_code | text | 邀请码 | NOT NULL, UNIQUE |
| created_by | bigint | 创建者用户ID | NOT NULL |
| expires_at | timestamptz | 过期时间 | NULL |
| max_uses | integer | 最大使用次数 | NULL |
| current_uses | integer | 当前使用次数 | NOT NULL, DEFAULT 0 |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 支持通过邀请码加入工作空间
- 可以设置过期时间和使用次数限制

---

### 2.21 join_requests - 加入请求表

存储用户申请加入工作空间的请求。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 请求ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| requester_uid | bigint | 请求者用户ID | NOT NULL |
| status | text | 状态 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 用于用户主动申请加入工作空间
- `status`字段标识请求状态（pending/approved/rejected等）

---

### 2.22 af_published_collab - 发布的协作对象表

存储已发布的协作对象（公开访问）。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| workspace_id | uuid | 工作空间ID | PRIMARY KEY, FK -> af_workspace(workspace_id) |
| doc_name | text | 文档名称 | PRIMARY KEY |
| published_by | bigint | 发布者用户ID | NOT NULL, FK -> af_user(uid) |
| metadata | jsonb | 元数据（JSON） | NOT NULL |
| blob | bytea | 发布数据（二进制） | NOT NULL, DEFAULT '' |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**触发器：**
- `af_published_collab_update_updated_at` - 自动更新updated_at

**说明：**
- 存储已发布的协作对象，允许公开访问
- 通过工作空间的`publish_namespace`生成公开URL

---

### 2.23 af_published_view_comment - 发布视图评论表

存储发布视图的评论。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 评论ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| doc_name | text | 文档名称 | NOT NULL |
| commenter | jsonb | 评论者信息（JSON） | NOT NULL |
| content | text | 评论内容 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储发布视图的评论
- `commenter`字段存储JSON格式的评论者信息

---

### 2.24 af_published_view_reaction - 发布视图反应表

存储发布视图的反应（点赞等）。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 反应ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| doc_name | text | 文档名称 | NOT NULL |
| reactor | jsonb | 反应者信息（JSON） | NOT NULL |
| reaction_type | text | 反应类型 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储发布视图的反应（如点赞、收藏等）
- `reaction_type`字段标识反应类型

---

### 2.25 af_template_category - 模板分类表

存储模板分类信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| category_id | uuid | 分类ID | PRIMARY KEY |
| name | text | 分类名称 | NOT NULL |
| icon | text | 图标 | NULL |
| bg_color | text | 背景颜色 | NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

---

### 2.26 af_template_creator - 模板创建者表

存储模板创建者信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| creator_id | uuid | 创建者ID | PRIMARY KEY |
| name | text | 创建者名称 | NOT NULL |
| avatar_url | text | 头像URL | NULL |
| number_of_templates | int | 模板数量 | NOT NULL, DEFAULT 0 |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

---

### 2.27 af_template_creator_account_link - 模板创建者账号链接表

存储模板创建者的账号链接。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| creator_id | uuid | 创建者ID | PRIMARY KEY, FK -> af_template_creator(creator_id) |
| link_type | text | 链接类型 | PRIMARY KEY |
| url | text | 链接URL | NOT NULL |

---

### 2.28 af_template_view - 模板视图表

存储模板视图信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| view_id | uuid | 视图ID | PRIMARY KEY |
| name | text | 模板名称 | NOT NULL |
| description | text | 描述 | NOT NULL |
| about | text | 关于 | NOT NULL |
| view_url | text | 视图URL | NOT NULL |
| creator_id | uuid | 创建者ID | NOT NULL, FK -> af_template_creator(creator_id) |
| is_new_template | boolean | 是否新模板 | NOT NULL |
| is_featured | boolean | 是否精选 | NOT NULL |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

---

### 2.29 af_template_view_template_category - 模板分类关联表

存储模板和分类的关联关系。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| view_id | uuid | 视图ID | PRIMARY KEY, FK -> af_template_view(view_id) |
| category_id | uuid | 分类ID | PRIMARY KEY, FK -> af_template_category(category_id) |

---

### 2.30 af_related_template_view - 相关模板关联表

存储相关模板的关联关系。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| view_id | uuid | 视图ID | PRIMARY KEY, FK -> af_template_view(view_id) |
| related_view_id | uuid | 相关视图ID | PRIMARY KEY, FK -> af_template_view(view_id) |

---

### 2.31 af_subscription_plans - 订阅计划表

存储订阅计划配置。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 计划ID | PRIMARY KEY |
| plan_code | varchar(50) | 计划代码 | UNIQUE, NOT NULL |
| plan_name | varchar(100) | 计划名称 | NOT NULL |
| plan_name_cn | varchar(100) | 计划中文名称 | NOT NULL |
| monthly_price_yuan | decimal(10,2) | 月付价格（元） | NOT NULL, DEFAULT 0 |
| yearly_price_yuan | decimal(10,2) | 年付价格（元） | NOT NULL, DEFAULT 0 |
| cloud_storage_gb | integer | 云存储空间（GB，-1=无，0=无限制） | DEFAULT -1 |
| has_inbox | boolean | 是否有收件箱 | NOT NULL, DEFAULT false |
| has_multi_device_sync | boolean | 是否支持多端同步 | NOT NULL, DEFAULT false |
| has_api_support | boolean | 是否支持API | NOT NULL, DEFAULT false |
| version_history_days | integer | 版本历史天数（-1=无） | DEFAULT -1 |
| ai_chat_count_per_month | integer | AI对话次数/月（-1=无限制） | DEFAULT -1 |
| ai_image_generation_per_month | integer | 图片生成次数/月（-1=无限制） | DEFAULT -1 |
| has_share_link | boolean | 是否有分享链接 | NOT NULL, DEFAULT false |
| has_publish | boolean | 是否有发布功能 | NOT NULL, DEFAULT false |
| workspace_member_limit | integer | 工作区成员数量限制（-1=无限制） | DEFAULT -1 |
| collaborative_workspace_limit | integer | 协作工作区数量限制（-1=无限制，0=仅限1个） | DEFAULT -1 |
| page_permission_guest_editors | integer | 页面权限访客编辑数量（-1=无，0=仅查看） | DEFAULT -1 |
| has_space_member_management | boolean | 是否有空间成员管理 | NOT NULL, DEFAULT false |
| has_space_member_grouping | boolean | 是否有空间成员分组 | NOT NULL, DEFAULT false |
| is_active | boolean | 是否激活 | NOT NULL, DEFAULT true |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**默认计划：**
- free_local（免费版本地）
- student（学生版）
- standard（标准版）
- team（团队版）
- enterprise（企业/校园版）

---

### 2.32 af_user_subscriptions - 用户订阅表

存储用户的订阅信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 订阅ID | PRIMARY KEY |
| uid | bigint | 用户ID | NOT NULL, FK -> af_user(uid) |
| plan_id | integer | 计划ID | NOT NULL, FK -> af_subscription_plans(id) |
| billing_type | varchar(20) | 计费类型（monthly/yearly） | NOT NULL |
| status | varchar(20) | 订阅状态（active/canceled/expired/pending） | NOT NULL, DEFAULT 'active' |
| start_date | timestamptz | 开始日期 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| end_date | timestamptz | 结束日期 | NOT NULL |
| canceled_at | timestamptz | 取消日期 | NULL |
| cancel_reason | text | 取消原因 | NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `idx_af_user_subscriptions_uid` (uid)
- `idx_af_user_subscriptions_plan_id` (plan_id)
- `idx_af_user_subscriptions_status` (status)
- `idx_af_user_subscriptions_end_date` (end_date)

---

### 2.33 af_subscription_addons - 订阅补充包表

存储订阅补充包配置。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 补充包ID | PRIMARY KEY |
| addon_code | varchar(50) | 补充包代码 | UNIQUE, NOT NULL |
| addon_name | varchar(100) | 补充包名称 | NOT NULL |
| addon_name_cn | varchar(100) | 补充包中文名称 | NOT NULL |
| addon_type | varchar(20) | 补充包类型（storage/ai_token） | NOT NULL |
| price_yuan | decimal(10,2) | 价格（元） | NOT NULL |
| storage_gb | integer | 存储空间（GB，仅当addon_type=storage时有效） | NULL |
| ai_chat_count | integer | AI对话次数（仅当addon_type=ai_token时有效） | NULL |
| ai_image_count | integer | AI图片生成次数（仅当addon_type=ai_token时有效） | NULL |
| is_active | boolean | 是否激活 | NOT NULL, DEFAULT true |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**默认补充包：**
- storage_5gb, storage_20gb, storage_50gb（存储补充包）
- ai_token_100, ai_token_400, ai_token_1000（AI Token补充包）

---

### 2.34 af_user_addons - 用户补充包表

存储用户购买的补充包。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 补充包ID | PRIMARY KEY |
| uid | bigint | 用户ID | NOT NULL, FK -> af_user(uid) |
| addon_id | integer | 补充包ID | NOT NULL, FK -> af_subscription_addons(id) |
| quantity | integer | 购买数量 | NOT NULL, DEFAULT 1 |
| start_date | timestamptz | 开始日期 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| end_date | timestamptz | 结束日期 | NOT NULL |
| status | varchar(20) | 状态（active/expired/used） | NOT NULL, DEFAULT 'active' |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `idx_af_user_addons_uid` (uid)
- `idx_af_user_addons_addon_id` (addon_id)
- `idx_af_user_addons_status` (status)
- `idx_af_user_addons_end_date` (end_date)

---

### 2.35 af_user_subscription_usage - 用户订阅使用记录表

记录用户的订阅使用情况（AI使用量等）。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 记录ID | PRIMARY KEY |
| uid | bigint | 用户ID | NOT NULL, FK -> af_user(uid) |
| subscription_id | integer | 订阅ID | NULL, FK -> af_user_subscriptions(id) |
| usage_date | date | 使用日期 | NOT NULL |
| usage_type | varchar(50) | 使用类型（ai_chat/ai_image/storage_bytes） | NOT NULL |
| usage_count | bigint | 使用次数或使用量 | NOT NULL, DEFAULT 0 |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `idx_af_user_subscription_usage_uid` (uid)
- `idx_af_user_subscription_usage_date` (usage_date)
- `idx_af_user_subscription_usage_type` (usage_type)
- `idx_af_user_subscription_usage_unique` (uid, usage_date, usage_type) UNIQUE

**说明：**
- 按日期记录用户的使用情况
- 用于统计和限制用户的使用量

---

### 2.36 af_workspace_ai_usage - 工作空间AI使用记录表

记录工作空间的AI使用情况。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 记录ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| usage_date | date | 使用日期 | NOT NULL |
| usage_type | varchar(50) | 使用类型 | NOT NULL |
| usage_count | bigint | 使用次数 | NOT NULL, DEFAULT 0 |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 按工作空间统计AI使用情况

---

### 2.37 af_ai_responses_monthly_tracking - AI响应月度跟踪表

按月跟踪AI响应使用情况。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | serial | 记录ID | PRIMARY KEY |
| uid | bigint | 用户ID | NOT NULL |
| year | integer | 年份 | NOT NULL |
| month | integer | 月份 | NOT NULL |
| response_count | integer | 响应次数 | NOT NULL, DEFAULT 0 |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**唯一约束：**
- (uid, year, month) UNIQUE

**说明：**
- 按月统计用户的AI响应使用情况
- 用于限制月度使用量

---

### 2.38 af_notification - 通知表

存储系统通知。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 通知ID | PRIMARY KEY, DEFAULT uuid_generate_v4() |
| workspace_id | uuid | 工作空间ID | NULL |
| notification_type | text | 通知类型 | NOT NULL |
| payload | jsonb | 通知内容（JSON） | NOT NULL |
| recipient_uid | bigint | 接收者用户ID | NULL |
| processed | boolean | 是否已处理 | NOT NULL, DEFAULT false |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT now() |

**触发器：**
- `af_notification_insert_trigger` - 插入时通过pg_notify发送通知

**说明：**
- 存储系统通知（如工作空间邀请、评论等）
- 通过PostgreSQL的NOTIFY机制实现实时通知

---

### 2.39 af_page_mention - 页面提及表

存储页面提及信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 提及ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| view_id | uuid | 视图ID | NOT NULL |
| view_name | text | 视图名称 | NULL |
| mentioned_uid | bigint | 被提及用户ID | NOT NULL |
| mentioner_uid | bigint | 提及者用户ID | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 记录用户在页面中被@提及的情况

---

### 2.40 af_page_mention_notification - 页面提及通知表

存储页面提及通知。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 通知ID | PRIMARY KEY |
| mention_id | uuid | 提及ID | NOT NULL |
| recipient_uid | bigint | 接收者用户ID | NOT NULL |
| status | text | 状态 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储页面提及的通知记录
- `status`字段标识通知状态（unread/read等）

---

### 2.41 af_access_request - 访问请求表

存储访问请求信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 请求ID | PRIMARY KEY |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| requester_uid | bigint | 请求者用户ID | NOT NULL |
| status | text | 状态 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储用户请求访问工作空间的记录

---

### 2.42 af_import_task - 导入任务表

存储数据导入任务。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 任务ID | PRIMARY KEY |
| uid | bigint | 用户ID | NOT NULL |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| file_path | text | 文件路径 | NOT NULL |
| status | text | 状态 | NOT NULL |
| progress | integer | 进度（百分比） | NULL |
| error_message | text | 错误信息 | NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储数据导入任务的状态和进度

---

### 2.43 af_quick_note - 快速笔记表

存储快速笔记。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 笔记ID | PRIMARY KEY |
| uid | bigint | 用户ID | NOT NULL |
| workspace_id | uuid | 工作空间ID | NOT NULL |
| content | text | 笔记内容 | NOT NULL |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 存储用户的快速笔记

---

### 2.44 af_workspace_namespace - 工作空间命名空间表

存储工作空间的命名空间配置。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| workspace_id | uuid | 工作空间ID | PRIMARY KEY, FK -> af_workspace(workspace_id) |
| namespace | text | 命名空间 | NOT NULL, UNIQUE |
| created_at | timestamptz | 创建时间 | DEFAULT CURRENT_TIMESTAMP |

**说明：**
- 用于工作空间的命名空间管理

---

### 2.45 af_workspace_deleted - 工作空间删除记录表

存储已删除的工作空间记录。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| workspace_id | uuid | 工作空间ID | PRIMARY KEY |
| deleted_at | timestamptz | 删除时间 | NOT NULL |
| deleted_by | bigint | 删除者用户ID | NOT NULL |

**说明：**
- 记录已删除的工作空间，用于数据恢复

---

### 2.46 user_cloud_integrations - 用户云集成表

存储用户的第三方云服务集成信息。

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | uuid | 集成ID | PRIMARY KEY, DEFAULT gen_random_uuid() |
| provider | varchar(64) | 提供者（如baidu、dropbox） | NOT NULL |
| user_uid | bigint | 用户ID | NOT NULL, FK -> af_user(uid) |
| access_token | text | 访问令牌 | NULL |
| refresh_token | text | 刷新令牌 | NULL |
| expires_at | timestamptz | 过期时间 | NULL |
| scopes | text | 权限范围 | NULL |
| meta | jsonb | 元数据（JSON） | DEFAULT '{}' |
| is_revoked | boolean | 是否已撤销 | NOT NULL, DEFAULT false |
| last_refreshed_at | timestamptz | 最后刷新时间 | NULL |
| created_at | timestamptz | 创建时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| updated_at | timestamptz | 更新时间 | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

**索引：**
- `ux_user_provider` (user_uid, provider) UNIQUE
- `ix_user_cloud_integrations_provider` (provider)

**触发器：**
- `trg_update_user_cloud_integrations_modtime` - 自动更新updated_at

**说明：**
- 存储用户连接的第三方云服务（如百度网盘、Dropbox等）的OAuth令牌
- 每个用户每个提供者只能有一个集成记录

---

## 三、表关系说明

### 3.1 GoTrue与Cloud的关联

- `auth.users.id` (uuid) ↔ `af_user.uuid` (uuid)
- `auth.sign_in_logs.user_uid` (bigint) ↔ `af_user.uid` (bigint)

### 3.2 核心业务关系

1. **用户体系**
   - `af_user` ← `af_workspace` (owner_uid)
   - `af_user` ← `af_workspace_member` (uid)
   - `af_user` ← `af_collab` (owner_uid)
   - `af_user` ← `af_collab_member` (uid)

2. **工作空间体系**
   - `af_workspace` ← `af_workspace_member` (workspace_id)
   - `af_workspace` ← `af_collab` (workspace_id)
   - `af_workspace` ← `af_chat` (workspace_id)
   - `af_workspace` ← `af_blob_metadata` (workspace_id)

3. **协作对象体系**
   - `af_collab` ← `af_collab_member` (oid)
   - `af_collab` ← `af_collab_snapshot` (oid)
   - `af_collab` ← `af_collab_embeddings` (oid, partition_key)
   - `af_collab` ← `af_snapshot_meta` (oid)
   - `af_collab` ← `af_snapshot_state` (oid)

4. **订阅体系**
   - `af_user` ← `af_user_subscriptions` (uid)
   - `af_subscription_plans` ← `af_user_subscriptions` (plan_id)
   - `af_user` ← `af_user_addons` (uid)
   - `af_subscription_addons` ← `af_user_addons` (addon_id)
   - `af_user` ← `af_user_subscription_usage` (uid)

---

## 四、重要说明

### 4.1 分区表

以下表使用PostgreSQL分区表，按`partition_key`分区：
- `af_collab` - 按协作对象类型分区
- `af_snapshot_meta` - 按快照类型分区
- `af_snapshot_state` - 按状态类型分区

### 4.2 触发器

多个表使用触发器实现自动功能：
- 自动更新`updated_at`字段
- 通过`pg_notify`实现实时通知
- 自动添加工作空间成员
- 防止重置加密签名

### 4.3 索引策略

- 外键字段通常都有索引
- 查询频繁的字段组合创建联合索引
- 时间字段通常按降序索引（用于查询最新记录）
- 向量字段使用HNSW索引（pgvector扩展）

### 4.4 数据安全

- 密码存储：GoTrue使用bcrypt加密存储密码
- 令牌存储：所有一次性令牌存储哈希值，不存储明文
- 数据加密：`af_user.encryption_sign`用于加密用户数据
- 软删除：多个表使用`deleted_at`字段实现软删除

---

## 五、常用查询示例

### 5.1 查询用户及其工作空间

```sql
SELECT u.uid, u.name, u.email, w.workspace_id, w.workspace_name
FROM af_user u
LEFT JOIN af_workspace_member wm ON u.uid = wm.uid
LEFT JOIN af_workspace w ON wm.workspace_id = w.workspace_id
WHERE u.uid = ?;
```

### 5.2 查询工作空间成员

```sql
SELECT u.uid, u.name, u.email, r.name as role_name
FROM af_workspace_member wm
JOIN af_user u ON wm.uid = u.uid
JOIN af_roles r ON wm.role_id = r.id
WHERE wm.workspace_id = ?;
```

### 5.3 查询用户订阅信息

```sql
SELECT us.*, sp.plan_name, sp.plan_name_cn
FROM af_user_subscriptions us
JOIN af_subscription_plans sp ON us.plan_id = sp.id
WHERE us.uid = ? AND us.status = 'active';
```

### 5.4 查询协作对象及其成员

```sql
SELECT c.oid, c.partition_key, cm.uid, u.name, p.name as permission_name
FROM af_collab c
LEFT JOIN af_collab_member cm ON c.oid = cm.oid
LEFT JOIN af_user u ON cm.uid = u.uid
LEFT JOIN af_permissions p ON cm.permission_id = p.id
WHERE c.workspace_id = ?;
```

---

## 六、维护建议

1. **定期清理**
   - 清理过期的刷新令牌
   - 清理过期的快照数据
   - 清理已删除的协作对象

2. **性能优化**
   - 定期分析表统计信息
   - 重建索引（如需要）
   - 监控分区表的数据分布

3. **备份策略**
   - 定期备份关键表数据
   - 备份用户数据和协作对象数据
   - 保留迁移历史记录

---

## 七、版本历史

- 2026-01-23：创建文档，包含GoTrue和Cloud主要表结构说明

---

**文档维护者：** AI Assistant  
**最后更新：** 2026-01-23

