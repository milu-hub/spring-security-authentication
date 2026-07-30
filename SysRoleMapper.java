package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper  // 交给 Spring 管理
public interface SysRoleMapper extends BaseMapper<SysRole> {
    // 空着就行！所有单表 CRUD 方法（插入、删除、更新、查询、分页）都继承了
}