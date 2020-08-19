package com.smart.example.service.impl;

import com.smart.example.config.ShiroConfig;
import com.smart.example.entity.User;
import com.smart.example.mapper.UserMapper;
import com.smart.example.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     *
     * @param user
     * @return  true 注册成功
     */

    @Override
    public boolean register(User user) {
        int count = 0;
        if (check(user.getUsername()) == false){
                /*
                String algorithmName, 加密算法
                Object source, 加密的密码
                Object salt,  加密盐  如果要设置一般使用用户名作为加密盐 也可以使用自定义的字符串 比如 uuid
                int hashIterations  加密的次数
                 */
            SimpleHash simpleHash = new SimpleHash(Sha256Hash.ALGORITHM_NAME,
                    user.getPassword(),
                    null,
                    ShiroConfig.HASH_ITERATIONS);
            //保存加密密码：注意ShiroConfig设置的加密方式，没有配就用toString
            user.setPassword(simpleHash.toHex());
             count = userMapper.insert(user);
        }else{
            throw  new  RuntimeException("账号已经存在");
        }
        return count !=0;
    }

    @Override
    public boolean check(String username) {
        User user = userMapper.findUserByName(username);
        if (user == null){
            return false;
        }
        return  true;
    }


}
