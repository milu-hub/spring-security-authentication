package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class SecurityTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityTestApplication.class, args);
    }
}
