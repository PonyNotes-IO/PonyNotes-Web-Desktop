package com.ruoyi.xmbj.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用版本领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth.app_version")
public class XmAppVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version_name", nullable = false, length = 100)
    private String versionName;

    @Column(name = "system_type", nullable = false, length = 50)
    private String systemType;

    @Column(name = "version_code", nullable = false, length = 50)
    private String versionCode;

    @Column(name = "update_desc", columnDefinition = "TEXT")
    private String updateDesc;

    @Column(name = "download_url", length = 255)
    private String downloadUrl;

    @Column(length = 20)
    private String status; // '正常', '停用'

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(length = 50)
    private String operator;

    @Column(name = "operator_id", length = 50)
    private String operatorId;
}