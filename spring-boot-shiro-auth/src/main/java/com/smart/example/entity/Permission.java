package com.smart.example.entity;

import lombok.Data;

/**
    * 权限表
    */
@Data
public class Permission {
    /**
    * 主键
    */
    private Integer perId;

    /**
    * 权限名称
    */
    private String perName;

    /**
    * 权限说明
    */
    private String description;

    /**
    * 是否启用
    */
    private Integer isDel;
}