package com.zfk.springboot_jsp_shiro.service;

import com.zfk.springboot_jsp_shiro.dao.UserDao;
import com.zfk.springboot_jsp_shiro.entity.Perm;
import com.zfk.springboot_jsp_shiro.entity.Role;
import com.zfk.springboot_jsp_shiro.entity.User;
import com.zfk.springboot_jsp_shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        // 处理业务调用dao
        // 此处需要对密码进行 md5 + salt + hash散列
        // 生成随机盐
        String salt = SaltUtils.getSalt(8);
        // 将随机盐保存到数据
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getSalt(), 1024);
        user.setPassword(md5Hash.toHex());
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findRolesByUsername(String username) {
        return userDao.findRolesByUsername(username);
    }

    @Override
    public List<Perm> findPermsByRoleId(String id) {
        return userDao.findPermsByRoleId(id);
    }
}
