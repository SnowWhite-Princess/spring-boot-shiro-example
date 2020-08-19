package com.smart.example.entity;

import lombok.Data;

/**
    * 权限表
    */
@Data
public class Role {
    /**
    * 主键
    */
    private Integer roleId;

    /**
    * 角色的名称
    */
    private String roleName;

    /**
    * 角色说明
    */
    private String description;

    /**
    * 是否启用
    */
    private Integer isDel;



}