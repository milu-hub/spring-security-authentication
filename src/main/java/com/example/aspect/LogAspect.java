package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    public static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 切点：拦截 controller 包下的所有方法
    @Pointcut("execution(* com.example.controller.*.*(..))")
    public void controllerMethod() {}

    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();

        // 打印请求日志
        log.info("🚀 请求开始: [{}] {}", method, url);
        log.info("📦 参数: {}", joinPoint.getArgs());

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 打印响应日志
        long duration = System.currentTimeMillis() - start;
        log.info("✅ 请求结束: [{}] {}, 耗时: {}ms", method, url, duration);

        return result;
    }
}
