package com.smart.example.service;

import com.smart.example.entity.User;

public interface UserService {
    boolean  register(User user);
    boolean check(String username);
    boolean pwd(String password);
}
