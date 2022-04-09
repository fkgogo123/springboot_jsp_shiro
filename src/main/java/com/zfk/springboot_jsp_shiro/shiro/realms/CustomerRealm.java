package com.zfk.springboot_jsp_shiro.shiro.realms;

import com.zfk.springboot_jsp_shiro.entity.User;
import com.zfk.springboot_jsp_shiro.shiro.salt.MyByteSource;
import com.zfk.springboot_jsp_shiro.service.UserService;
import com.zfk.springboot_jsp_shiro.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class CustomerRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取主身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();

        // 根据主身份信息获取角色 和 权限信息
        System.out.println("调用授权验证：" + primaryPrincipal);
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.findRolesByUsername(primaryPrincipal);
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                userService.findPermsByRoleId(role.getId()).forEach(perm -> {
                    simpleAuthorizationInfo.addStringPermission(perm.getName());
                });
            });
            return simpleAuthorizationInfo;

        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=========================");
        // 主身份信息，这里是用户名
        String principal = (String) token.getPrincipal();

        // 在工厂中获取service对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        System.out.println(userService);
        // 根据用户名 数据库中的User对象
        User user = userService.findByUsername(principal);

        if (!ObjectUtils.isEmpty(user)){
            // 根据返回数据库中的对象， 密码的认证交给shiro去做。需要重写shiro的密码匹配器。在realm中set匹配器。
            // 数据库中的盐要通过工具类来获取
            // return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), new MyByteSource(user.getSalt()), this.getName());
        }
        return null;
    }

}
