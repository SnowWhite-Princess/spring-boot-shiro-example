package com.smart.example.mapper;

import com.smart.example.dto.PermissionDto;
import com.smart.example.dto.RoleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    List<RoleDto> selectRolesByUserId(@Param("uid") int uid);
}
