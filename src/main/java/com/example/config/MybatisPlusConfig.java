package com.example.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    // 暂时注释掉分页插件，待依赖解决后再启用
    /*
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    */
}