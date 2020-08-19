package com.smart.example.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class RoleDto implements Serializable {
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
    /**
     * 所有角色的权限信息
     */
    private List<PermissionDto> permissions;


}
