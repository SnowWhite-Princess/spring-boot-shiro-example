package com.smart.example.config;

import com.smart.example.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 必要的三个配置
 * 1> 自定义Realm
 * 注入到容器
 * SecurityManager中设置
 * <p>
 * 2> 过滤器
 * 3 SecurityManager对象
 * 4 加密
 * 散列算法   MD5   SHA   不可逆
 * 1> 配置类中
 * 1.1 注册
 * 2> 注册跟修改密码
 */
@Configuration
public class ShiroConfig {
    public static final int HASH_ITERATIONS = 1024;

    // 注册加密匹配器
    @Bean
    public UserRealm realm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置自定的Realm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 配置 url过滤器
     * 做统一的鉴权  即判断路径需要登录才能访问,哪些不需要
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition filterChainDefinition() {
        //登录接口不需要认证就能访问
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        // 表示不需要授权  (anon 表示访问登录不需要认证)
        definition.addPathDefinition("/login", "anon");
        definition.addPathDefinition("/register", "anon");
        definition.addPathDefinition("/druid/**", "anon");
        definition.addPathDefinition("/index", "anon");
        definition.addPathDefinition("/check", "anon");
        // 用户登出的操作
        definition.addPathDefinition("/logout", "logout");
        // authc 表示所有的api开头的url地址必须登录 403
        definition.addPathDefinition("/api/**", "authc");
        definition.addPathDefinition("/**", "authc");
        definition.addPathDefinition("/admin/**", "authc");
        return definition;
    }


    /**
     * 加密匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 使用md5密码匹配器
//          matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        //  使用SHA256 密码匹配器
        matcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        //设置加密次数
        matcher.setHashIterations(HASH_ITERATIONS);
        // 如果启用了注意注册和更新密码的时候使用 toHex方法
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }
}
