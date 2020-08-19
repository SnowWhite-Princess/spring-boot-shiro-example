package com.smart.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 */
@Data
public class User {
    private Integer uid;

    private String username;

    private String password;

    private Integer status;
}