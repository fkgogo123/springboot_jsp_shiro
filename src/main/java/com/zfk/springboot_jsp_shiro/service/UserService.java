package com.zfk.springboot_jsp_shiro.service;

import com.zfk.springboot_jsp_shiro.entity.Perm;
import com.zfk.springboot_jsp_shiro.entity.Role;
import com.zfk.springboot_jsp_shiro.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    // 注册用户方法
    void register(User user);

    User findByUsername(String username);

    // 根据用户名 查询角色
    User findRolesByUsername(String username);

    List<Perm> findPermsByRoleId(String id);
}
