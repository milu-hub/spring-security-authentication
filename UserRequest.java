package com.example.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;  // 前端传明文，后端加密后再存
    private String nickname;
    private Long roleId;
    private Integer status;   // 1启用，0禁用
}