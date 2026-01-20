package com.ruoyi.framework.annotation;

import java.lang.annotation.*;

/**
 * 指定数据源注解
 * 
 * 在 Service 或 Mapper 方法上使用该注解，指定该方法使用的数据源
 * 
 * 使用示例：
 * @DataSource(DataSourceType.slave) // 使用从库 (PostgreSQL)
 * public ClientUser getClientUserByUserInfo(String userInfo) {
 * return clientUserMapper.getClientUserByUserInfo(userInfo);
 * }
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源名称
     * 默认为 "master" (主库)
     * 可选值：
     * - "master": 主库 (MySQL)
     * - "slave": 从库 (PostgreSQL)
     */
    String value() default "master";
}
