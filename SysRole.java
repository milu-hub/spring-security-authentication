package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")  // 告诉 MyBatis-Plus 这个类对应哪张表
public class SysRole {

    @TableId(type = IdType.AUTO)  // 主键自增
    private Long id;

    private String roleName;    // 角色名称（中文）
    private String roleKey;     // 角色标识（英文）
    private String permCode;    // 权限字符串（如：user:add,user:edit）
    private Integer status;     // 状态（1启用，0禁用）

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
