package com.ruoyi.xmbj.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户信息对象 af_user
 *
 * @author ruoyi
 * @date 2025-12-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_user")
public class AfUser {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "uid")
    private Long uid;

    /** 关联认证系统的唯一标识 */
    private String uuid;

    /** 邮箱 */
    private String email;

    /** 密码 */
    private String password;

    /** 用户名 */
    private String name;

    /** 元数据 */
    private String metadata;

    /** 用于加密用户数据的签名 */
    private String encryptionSign;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

}