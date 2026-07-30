package com.example.util;

import com.example.result.response.Result;
import com.example.result.response.ResultCode;

public class ResponseUtil {

    public static <T> Result<T> success() {
        return Result.success();
    }

    public static <T> Result<T> success(T data) {
        return Result.success(data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return Result.success(msg, data);
    }

    public static <T> Result<T> error(String msg) {
        return Result.error(msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return Result.error(code, msg);
    }

    public static <T> Result<T> error(ResultCode resultCode) {
        return Result.error(resultCode);
    }
}