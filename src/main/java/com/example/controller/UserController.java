package com.example.controller;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.entity.SysUser;
import com.example.result.response.Result;
import com.example.result.response.PageResult;
import com.example.result.request.BasePageRequest;
import com.example.service.UserService;
import com.example.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ================== 核心接口 ==================

    /**
     * 1. 分页查询用户列表
     * GET /users?page=1&size=10&keyword=admin
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:list')")
    public Result<PageResult<UserResponse>> listUsers(BasePageRequest pageRequest,
                                                      @RequestParam(required = false) String keyword) {
        PageResult<UserResponse> pageResult = userService.pageQuery(pageRequest, keyword);
        return ResponseUtil.success(pageResult);
    }

    /**
     * 2. 查询单个用户
     * GET /users/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:query')")
    public Result<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserResponseById(id);
        return ResponseUtil.success(response);
    }

    /**
     * 3. 获取当前登录用户信息（前端登录后调用）
     * GET /users/me
     */
    @GetMapping("/me")
    public Result<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserResponse response = userService.getUserResponseByUsername(username);
        return ResponseUtil.success(response);
    }

    /**
     * 4. 新增用户
     * POST /users
     */
    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    public Result<Long> createUser(@Valid @RequestBody UserRequest request) {
        Long userId = userService.createUser(request);
        return ResponseUtil.success(userId);
    }

    /**
     * 5. 全量更新用户
     * PUT /users/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:edit')")
    public Result<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        userService.updateUser(id, request);
        return ResponseUtil.success();
    }

    /**
     * 6. 部分更新用户（只更新传了非空字段，如只改状态）
     * PATCH /users/{id}
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('user:edit')")
    public Result<Void> patchUser(@PathVariable Long id, @RequestBody UserRequest request) {
        userService.patchUser(id, request);
        return ResponseUtil.success();
    }

    /**
     * 7. 删除用户
     * DELETE /users/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseUtil.success();
    }
}