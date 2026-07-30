package com.example.dto;

import lombok.Data;

@Data
public class RoleRequest {
    private String roleName;
    private String roleKey;
    private String permCode;   // user:add,user:edit,user:delete
    private Integer status;
}