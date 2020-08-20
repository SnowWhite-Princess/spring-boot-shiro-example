package com.smart.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private Integer uid;
    private String username;
    private Integer status;
}

