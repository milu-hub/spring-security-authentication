package com.example.result.response;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    UNAUTHORIZED(401, "未登录或Token已过期"),
    FORBIDDEN(403, "权限不足，拒绝访问"),
    NOT_FOUND(404, "资源不存在"),
    PARAM_ERROR(400, "参数校验失败");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}