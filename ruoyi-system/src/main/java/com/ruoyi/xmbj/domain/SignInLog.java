package com.ruoyi.xmbj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("auth.sign_in_logs")
public class SignInLog {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Id
    @Column(name = "id")
    private String id;

    /** 用户ID */
    @Column(name = "user_uuid")
    private String userUuid;

    /** 用户UID */
    @Column(name = "user_uid")
    private Long userUid;

    /** 登录方式 */
    @Column(name = "provider")
    private String provider;

    /** 第三方ID */
    @Column(name = "third_party_id")
    private String thirdPartyId;

    /** IP地址 */
    @Column(name = "ip_address")
    private String ipAddress;

    /** 国家 */
    @Column(name = "country")
    private String country;

    /** 地区 */
    @Column(name = "region")
    private String region;

    /** 城市 */
    @Column(name = "city")
    private String city;

    /** 用户代理 */
    @Column(name = "user_agent")
    private String userAgent;

    /** 是否成功 */
    @Column(name = "success")
    private Boolean success;

    /** 错误原因 */
    @Column(name = "error_reason")
    private String errorReason;

    /** 元数据 */
    @Column(name = "metadata")
    private String metadata;

    /** 创建时间 */
    @Column(name = "created_at")
    private Date createdAt;
}