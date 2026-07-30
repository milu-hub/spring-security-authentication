package com.example.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.entity.SysRole;
import com.example.entity.SysUser;
import com.example.exception.BusinessException;
import com.example.mapper.SysRoleMapper;
import com.example.mapper.SysUserMapper;
import com.example.result.response.PageResult;
import com.example.result.request.BasePageRequest;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ====== 原有方法 ======
    @Override
    public SysUser getByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return sysUserMapper.selectOne(wrapper);
    }

    // ====== 新增业务方法 ======

    @Override
    public PageResult<UserResponse> pageQuery(BasePageRequest pageRequest, String keyword) {
        Page<SysUser> page = new Page<>(pageRequest.getPage(), pageRequest.getSize());
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like("username", keyword).or().like("nickname", keyword);
        }
        wrapper.orderByDesc("create_time");

        Page<SysUser> userPage = sysUserMapper.selectPage(page, wrapper);
        // 将 SysUser 转换为 UserResponse（同时查出角色名）
        PageResult<UserResponse> result = new PageResult<>();
        result.setTotal(userPage.getTotal());
        result.setPages((long) userPage.getPages());
        result.setCurrent(userPage.getCurrent());
        result.setSize(userPage.getSize());
        result.setRecords(userPage.getRecords().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public UserResponse getUserResponseById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return convertToResponse(user);
    }

    @Override
    public UserResponse getUserResponseByUsername(String username) {
        SysUser user = getByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return convertToResponse(user);
    }

    @Override
    public Long createUser(UserRequest request) {
        // 1. 检查用户名是否已被占用
        if (getByUsername(request.getUsername()) != null) {
            throw new BusinessException("用户名已被占用");
        }
        // 2. 转换为实体类
        SysUser user = new SysUser();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // 3. 保存
        sysUserMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(Long id, UserRequest request) {
        SysUser existing = sysUserMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("用户不存在");
        }
        // 全量覆盖（除了密码，密码单独处理）
        BeanUtils.copyProperties(request, existing);
        if (StringUtils.hasText(request.getPassword())) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        sysUserMapper.updateById(existing);
    }

    @Override
    public void patchUser(Long id, UserRequest request) {
        SysUser existing = sysUserMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("用户不存在");
        }
        // 只更新非空字段
        if (StringUtils.hasText(request.getNickname())) {
            existing.setNickname(request.getNickname());
        }
        if (request.getRoleId() != null) {
            existing.setRoleId(request.getRoleId());
        }
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }
        if (StringUtils.hasText(request.getPassword())) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        // username 一般不允许修改，若需要可自行扩展
        sysUserMapper.updateById(existing);
    }

    @Override
    public void deleteUser(Long id) {
        SysUser existing = sysUserMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("用户不存在");
        }
        sysUserMapper.deleteById(id);
    }

    // ====== 私有转换方法 ======
    private UserResponse convertToResponse(SysUser user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        // 联查角色名
        SysRole role = sysRoleMapper.selectById(user.getRoleId());
        if (role != null) {
            response.setRoleName(role.getRoleName());
        }
        return response;
    }
}