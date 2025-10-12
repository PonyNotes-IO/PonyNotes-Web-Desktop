package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruoyi.system.mapper.SysPostgresMapper;
import com.ruoyi.system.service.ISysPostgresService;


public class SysPostgresServiceImpl  implements ISysPostgresService{

    
    @Autowired
    private SysPostgresMapper sysPostgresMapper;
    @Override
    public List<Map<String, Object>> selectUserByPhone(String phone) {
        // TODO Auto-generated method stub
        return sysPostgresMapper.selectUserByPhone(phone);
    }

    @Override
    public List<Map<String, Object>> selectUserByEmail(String email) {
        // TODO Auto-generated method stub
        return sysPostgresMapper.selectUserByEmail(email);
    }
    

}
