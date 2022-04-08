package com.zfk.springboot_jsp_shiro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * CustomerRealm 不是 spring 工厂管理的， 没办法自动注入userService对象
 * 需要去工厂中拿到一个已经创建好的对象
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    // 根据bean的名字，获取工厂中指定名字的bean
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
}
