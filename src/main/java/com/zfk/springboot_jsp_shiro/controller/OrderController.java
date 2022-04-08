package com.zfk.springboot_jsp_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/save")
    // @RequiresRoles("admin") // 用来判断角色
    // @RequiresRoles(value = {"admin", "user"}) // 用来判断角色, 同时具有
    @RequiresPermissions("user:create:*") // 需要具有create 权限
    public String save() {
        System.out.println("进入order/save方法");

        // Subject subject = SecurityUtils.getSubject();
        // if (subject.hasRole("admin")) {
        //     System.out.println("保存订单");
        // } else {
        //     System.out.println("无权访问");
        // }

        return "redirect:/index.jsp";
    }
}
