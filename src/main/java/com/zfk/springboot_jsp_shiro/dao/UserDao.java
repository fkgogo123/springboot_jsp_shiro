package com.zfk.springboot_jsp_shiro.dao;

import com.zfk.springboot_jsp_shiro.entity.Perm;
import com.zfk.springboot_jsp_shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    void save(User user);

    User findByUsername(String username);

    // 根据用户名 查询角色
    User findRolesByUsername(String username);

    // 根据角色id 查询权限集合
    List<Perm> findPermsByRoleId(String id);
}
