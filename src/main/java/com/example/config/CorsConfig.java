package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有接口
                .allowedOriginPatterns("*") // 允许所有源（生产环境请替换为具体前端域名）
                .allowedMethods("*") // 允许所有请求方法（GET,POST,PUT,DELETE...）
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 允许携带凭证（如 Cookie）
                .maxAge(3600); // 预检请求缓存时间
    }
}
