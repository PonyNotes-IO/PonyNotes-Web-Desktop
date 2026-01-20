package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

public interface ISysPostgresService {
    public List<Map<String, Object>> selectUserByPhone(String phone);

    public List<Map<String, Object>> selectUserByEmail(String email);

}
