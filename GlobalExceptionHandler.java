package com.example.exception;

import com.example.result.response.Result;
import com.example.result.response.ResultCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    // 2. 处理登录认证异常（用户名不存在 / 密码错误）
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public Result<Void> handleAuthenticationException(RuntimeException e) {
        return Result.error(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
    }

    // 3. 处理参数校验异常（@Valid 校验失败）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        // 只取第一个错误提示返回给前端
        String message = errors.isEmpty() ? "参数校验失败" : errors.get(0).getDefaultMessage();
        return Result.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    // 4. 处理其他所有未捕获的异常（兜底）
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        e.printStackTrace(); // 方便开发阶段查看具体报错
        return Result.error(ResultCode.ERROR.getCode(), "系统繁忙，请稍后重试");
    }
}