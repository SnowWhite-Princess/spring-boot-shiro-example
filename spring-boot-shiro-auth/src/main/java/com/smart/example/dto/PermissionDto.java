package com.smart.example.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class PermissionDto  implements Serializable {
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
