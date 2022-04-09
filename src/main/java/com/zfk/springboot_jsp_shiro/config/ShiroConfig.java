package com.zfk.springboot_jsp_shiro.config;

import com.zfk.springboot_jsp_shiro.shiro.cache.RedisCacheManager;
import com.zfk.springboot_jsp_shiro.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {

    // 1.创建shiroFilter // 负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 给filter 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 配置 系统受限资源
        // 配置 系统公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/user/login", "anon"); // 允许匿名访问（公共资源）
        map.put("/user/register", "anon"); // 允许匿名访问（公共资源）
        map.put("/register.jsp", "anon"); // 允许匿名访问（公共资源）
        map.put("/user/getImage", "anon"); // 允许匿名访问（公共资源）获取验证码

        map.put("/index.jsp", "anon"); // 允许匿名访问（公共资源）

        map.put("/**", "authc");  // "authc" 请求这个资源需要认证和授权. 实际是个过滤器，既要认证又要授权
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        // 如果不写认证路径。访问受限资源时shiro默认 重定向 到login页面
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        return shiroFilterFactoryBean;
    }

    // 2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 给安全管理器设置Realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    // 3. 自定义realm
    @Bean
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        // 需要修改密码匹配器。
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);

        // 开启缓存管理
        // 继承关系：customerRealm --> AuthorizingRealm --> AuthenticatingRealm --> CachingRealm
        // customerRealm.setCacheManager(new EhCacheManager()); //这是本地缓存，可以使用redis等实现分布式缓存
        customerRealm.setCacheManager(new RedisCacheManager());
        customerRealm.setCachingEnabled(true); // 开启全局缓存
        customerRealm.setAuthenticationCachingEnabled(true); // 开启认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache"); // 可选，设置缓存名字
        customerRealm.setAuthorizationCachingEnabled(true); // 开启授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");

        return customerRealm;
    }

}
