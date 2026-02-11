package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 临时 SQL 执行控制器
 * 用于执行数据库表结构修改等操作
 */
@RestController
@RequestMapping("/temp/sql")
public class TempSqlController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行 SQL 语句
     */
    @PostMapping("/execute")
    public AjaxResult executeSql(@RequestBody String sql) {
        try {
            // 执行 SQL 语句
            jdbcTemplate.execute(sql);
            return AjaxResult.success("SQL 语句执行成功");
        } catch (Exception e) {
            return AjaxResult.error("SQL 语句执行失败: " + e.getMessage());
        }
    }
}
