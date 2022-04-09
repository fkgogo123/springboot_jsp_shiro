package com.zfk.springboot_jsp_shiro.controller;

import com.zfk.springboot_jsp_shiro.entity.User;
import com.zfk.springboot_jsp_shiro.service.UserService;
import com.zfk.springboot_jsp_shiro.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 验证码方法
     * 验证码code，最好放在Session中，
     * 验证码图片以response流的形式输出
     */
    @RequestMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 生成验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 验证码存入图片
        session.setAttribute("verifyCode", verifyCode);
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, os, verifyCode);

    }


    /**
     * 用户注册
     */
    @RequestMapping("/register")
    public String register(User user) {
        try {
            userService.register(user);
            return "redirect:/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

    /**
     * 用来处理身份认证
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, String vertifyCode, HttpSession session) {
        // 获取验证码
        String setVerifyCode = (String) session.getAttribute("verifyCode");
        try {
            if (setVerifyCode.equalsIgnoreCase(vertifyCode)) {
                // 1.获取主体
                Subject subject = SecurityUtils.getSubject();

                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
                return "redirect:/index.jsp";
            } else {
                throw new RuntimeException("验证码错误");
            }

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return "redirect:/login.jsp";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }
}
