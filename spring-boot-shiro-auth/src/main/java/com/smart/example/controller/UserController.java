package com.smart.example.controller;

import com.smart.example.common.ResponseResult;
import com.smart.example.dto.UserDto;
import com.smart.example.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public ResponseResult<UserDto> getUserInfo(){
        UserDto userDto = (UserDto)SecurityUtils.getSubject().getPrincipal();
        return ResponseResult.success(userDto);
    }

    @GetMapping("/pwd")
    public ResponseResult<UserDto> changePassword(String password){
        UserDto userDto = (UserDto)SecurityUtils.getSubject().getPrincipal();
        return ResponseResult.success(userDto);
    }

    @GetMapping("/change")
    public ResponseResult<UserDto> changeUser(){
        UserDto userDto = (UserDto)SecurityUtils.getSubject().getPrincipal();
        return ResponseResult.success(userDto);
    }

}
