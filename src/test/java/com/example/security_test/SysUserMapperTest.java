package com.example.security_test;

import com.example.entity.SysUser;
import com.example.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void testSelectById() {
        SysUser sysUser = sysUserMapper.selectById(1);
        System.out.println("select success:"+sysUser);
    }
}
