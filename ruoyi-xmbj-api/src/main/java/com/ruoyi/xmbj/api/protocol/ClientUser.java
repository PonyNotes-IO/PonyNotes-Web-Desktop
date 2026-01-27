package com.ruoyi.xmbj.api.protocol;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 客户端用户信息模型
 * 对应 XMBJ 系统中的 af_user 表结构
 */
public class ClientUser implements Serializable {
    private Long uid;                     // 用户ID
    private UUID uuid;                    // 用户UUID
    private String email;                 // 邮箱
    private String password;              // 密码
    private String name;                  // 用户名
    private Object metadata;              // 用户元数据（JSONB）
    private String encryptionSign;        // 加密签名
    private LocalDateTime deletedAt;      // 删除时间
    private LocalDateTime updatedAt;      // 更新时间
    private LocalDateTime createdAt;      // 创建时间
    private String phone;                 // 手机号

    // 构造函数
    public ClientUser() {}

    public ClientUser(Long uid, UUID uuid, String email, String password, String name, 
                     Object metadata, String encryptionSign, LocalDateTime deletedAt, 
                     LocalDateTime updatedAt, LocalDateTime createdAt, String phone) {
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

    // Getter和Setter方法
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("encryption_sign")
    public String getEncryptionSign() {
        return encryptionSign;
    }

    @JsonProperty("encryption_sign")
    public void setEncryptionSign(String encryptionSign) {
        this.encryptionSign = encryptionSign;
    }

    @JsonProperty("deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    @JsonProperty("deleted_at")
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
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