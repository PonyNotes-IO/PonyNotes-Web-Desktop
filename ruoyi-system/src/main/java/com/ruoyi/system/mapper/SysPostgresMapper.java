package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface SysPostgresMapper {
    @Select("SELECT id, phone,email FROM users where phone = #{phone} LIMIT 1")
    List<Map<String, Object>> selectUserByPhone(String username);

    @Select("SELECT id, phone,email FROM users where email = #{email} LIMIT 1")
    List<Map<String, Object>> selectUserByEmail(String email);
}
