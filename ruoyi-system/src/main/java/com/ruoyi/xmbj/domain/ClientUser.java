package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * 客户端用户信息模型
 * 对应 XMBJ 系统中的 af_user 表结构
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ClientUser {
    private Long uid;                     // 用户ID
    private String uuid;                    // 用户UUID
    private String email;                 // 邮箱
    private String password;              // 密码
    private String name;                  // 用户名
    private Object metadata;              // 用户元数据（JSONB）
    private String encryptionSign;        // 加密签名
    private Date deletedAt;      // 删除时间
    private Date updatedAt;      // 更新时间
    private Date createdAt;      // 创建时间
    private String phone;                 // 手机号

    // 构造函数
    public ClientUser() {}

    public ClientUser(Long uid, String uuid, String email, String password, String name,
                      Object metadata, String encryptionSign, Date deletedAt,
                      Date updatedAt, Date createdAt, String phone) {
        this.uid = uid;
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.metadata = metadata;
        this.encryptionSign = encryptionSign;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ClientUser{" +
                "uid=" + uid +
                ", uuid=" + uuid +
                ", email='" + email + '\'' +
                ", password='" + (password != null ? "***" : "null") + '\'' +
                ", name='" + name + '\'' +
                ", metadata=" + metadata +
                ", encryptionSign='" + encryptionSign + '\'' +
                ", deletedAt=" + deletedAt +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", phone='" + phone + '\'' +
                '}';
    }
}