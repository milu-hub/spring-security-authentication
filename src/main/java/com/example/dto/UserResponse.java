package com.example.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String nickname;
    private Long roleId;
    private String roleName;   // 联查角色表得到的中文名
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}