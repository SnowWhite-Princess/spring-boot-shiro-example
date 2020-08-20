package com.smart.example.utils;

import com.smart.example.dto.UserDto;
import org.apache.shiro.session.Session;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    //循环次数
    public static final int HASH_ITERATIONS = 1024;

    public static String sha256(String password,String salt){
        return new SimpleHash(Sha256Hash.ALGORITHM_NAME,password,salt,HASH_ITERATIONS).toHex();
    }

    /**
     * 获取会话
     */

    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * Subject:主体，代表了当前“用户”
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static UserDto getUser(){
        return (UserDto) SecurityUtils.getSubject().getPrincipal();
    }


    public static Integer getUserId(){
        return getUser().getUid();
    }

    public static void setSessionAttribute(Object key,Object value){
      getSession().setAttribute(key,value);
    }

    public static Object getSessionAttribute(Object key){
        return getSession().getAttribute(key);
    }

    public static boolean isLogin(){
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

}
