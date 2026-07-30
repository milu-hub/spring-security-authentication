package com.example.service.imp;

import com.example.entity.SysRole;
import com.example.entity.SysUser;
import com.example.mapper.SysRoleMapper;
import com.example.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {  // ✅ 建议改名为 Impl

    // 1. 声明为 final，保证不可变
    private final UserService userService;
    private final SysRoleMapper sysRoleMapper;

    // 2. 构造器注入（Spring Boot 3 会自动识别唯一构造器，不需要 @Autowired）
    public UserDetailsServiceImpl(UserService userService, SysRoleMapper sysRoleMapper) {
        this.userService = userService;
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 通过业务层查询用户
        SysUser sysUser = userService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 2. 查询角色
        SysRole sysRole = sysRoleMapper.selectById(sysUser.getRoleId());
        if (sysRole == null) {
            throw new UsernameNotFoundException("用户未分配角色");
        }

        // 3. 构建权限列表
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleKey()));
        if (sysRole.getPermCode() != null && !sysRole.getPermCode().isEmpty()) {
            for (String perm : sysRole.getPermCode().split(",")) {
                authorities.add(new SimpleGrantedAuthority(perm.trim()));
            }
        }

        // 4. 返回 UserDetails 对象
        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getStatus() == 1,
                true,
                true,
                true,
                authorities
        );
    }
}