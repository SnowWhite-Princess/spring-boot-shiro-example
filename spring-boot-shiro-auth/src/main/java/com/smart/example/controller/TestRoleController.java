package com.smart.example.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 403没有相关的访问权限
@RestController
public class TestRoleController {
    @GetMapping("/")
    public String index(){
        return "不需要任何权限";
    }

    @GetMapping("/role/admin")
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    public String testAdmin(){
        return "只有admin角色才能访问";
    }

    @GetMapping("/per/create")
    @RequiresPermissions("user:create")
    public String testPermission(){
        return "创建用户信息的权限";
    }

}
