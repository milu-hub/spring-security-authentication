package com.example.service;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.entity.SysUser;
import com.example.result.response.PageResult;
import com.example.result.request.BasePageRequest;

public interface UserService {
    // 原有方法
    SysUser getByUsername(String username);

    // 新增方法
    PageResult<UserResponse> pageQuery(BasePageRequest pageRequest, String keyword);
    UserResponse getUserResponseById(Long id);
    UserResponse getUserResponseByUsername(String username);
    Long createUser(UserRequest request);
    void updateUser(Long id, UserRequest request);
    void patchUser(Long id, UserRequest request);
    void deleteUser(Long id);
}