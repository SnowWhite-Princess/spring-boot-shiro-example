package com.smart.example.realm;

import com.smart.example.dto.PermissionDto;
import com.smart.example.dto.RoleDto;
import com.smart.example.entity.User;
import com.smart.example.mapper.RolePermissionMapper;
import com.smart.example.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    @Resource
    UserMapper userMapper;
    @Resource
    RolePermissionMapper rolePermissionMapper;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取的是通过认证  simpleAuthenticationInfo对象的第一个参数
        User user = (User)principalCollection.getPrimaryPrincipal();
        //通过用户信息查询角色表 权限表
        Integer uid = user.getUid();
        List<RoleDto> roleList = rolePermissionMapper.selectRolesByUserId(uid);
        Set<String> roles = new HashSet<>();
        List<String> permissions = new ArrayList<>();
        for (RoleDto role : roleList){
            roles.add(role.getRoleName());
            for (PermissionDto permission: role.getPermissions()){
                permissions.add(permission.getPerName());
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 通过用户名查
     *
     *
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 第一步获取用户输入的用户名
        String username = (String) authenticationToken.getPrincipal();
        // 第二部 从数据中查询用户对象
        User user = userMapper.findUserByName(username);
        // 账号不存在
        if (user== null){
            throw new UnknownAccountException("账号不存在");
        }
        // 1表示账号被禁用 0表示正常
        if (user.getStatus()==1){
            throw  new  LockedAccountException("表示账号被禁用!请与管理员联系!!!");
        }

        /**
         * Object principal 必要参数, 从数据库中查询的用户对象
         * Object hashedCredentials 必要参数, 从数据库获取的密码
         * ByteSource credentialsSalt 必须要的可选,  混淆加密,增加破解的难度
         * String realmName 必要   用户输入的用户名
         */
        //需要返回AuthenticationInfo对象，AuthenticationInfo是一个接口，simpleAuthenticationInfo是该对象的实现类
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        return simpleAuthenticationInfo;
    }
}
