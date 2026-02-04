package com.ruoyi.xmbj.domain;

import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户使用统计领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_usage")
public class UserUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Default
    @Column(name = "storage_used")
    private Long storageUsed = 0L; // 字节数

    @Default
    @Column(name = "ai_calls_made")
    private Integer aiCallsMade = 0;

    @Default
    @Column(name = "priority_count")
    private Integer priorityCount = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_updated")
    private Date lastUpdated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
        this.lastUpdated = now;
    }

    @PreUpdate
    protected void onUpdate() {
        Date now = new Date();
        this.updatedAt = now;
        this.lastUpdated = now;
    }
}
