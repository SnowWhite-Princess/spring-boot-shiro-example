package com.smart.example.controller;

import com.smart.example.entity.User;
import com.smart.example.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制层---->  自定义Realm ---->配置信息
 * 密码加密
 * nacos
 * feign
 * dubbo
 * <p>
 * 网关
 * 聚合服务
 * 基础服务
 * <p>
 * 权限的使用
 * 编程式
 * 注解
 */
@RestController
public class LoginController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public String login(String username, String password) {
        // access_token
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(usernamePasswordToken);
        } catch (CredentialsException e) {
            // 表示密码校验失败
            throw new ClassCastException("账号或密码错误");
        } catch (ExcessiveAttemptsException e) {
            throw new ExcessiveAttemptsException("尝试次数过多!请稍后再试");
        } catch (UnknownAccountException e) {
            throw new UnknownAccountException("登录失败!!!请稍后再试");
        } catch (Exception e) {
            throw new RuntimeException("登录失败!!!请稍后再试");
        }
        return "success";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        userService.register(user);
        return "success";
    }


    // 检查用户名是否存在
    @PostMapping("/check")
    public boolean check(String username) {
        boolean isExist = userService.check(username);
        return isExist;
    }
}
