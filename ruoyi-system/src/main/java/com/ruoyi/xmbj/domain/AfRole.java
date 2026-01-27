package com.ruoyi.xmbj.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色信息对象 af_roles
 *
 * @author ruoyi
 * @date 2025-12-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("af_roles")
public class AfRole {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Integer id;

    /** 角色名称 */
    private String name;

}