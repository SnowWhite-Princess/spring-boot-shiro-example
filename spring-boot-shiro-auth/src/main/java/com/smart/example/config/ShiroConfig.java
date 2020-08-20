package com.smart.example.config;

import com.smart.example.listener.ShiroSessionListener;
import com.smart.example.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证
 * 授权
 * session管理
 * 缓存管理
 * 业务功能
 *
 * 必要的三个配置
 * 1> 自定义Realm
 * 注入到容器
 * SecurityManager中设置
 * 2> 过滤器
 * 3 SecurityManager对象
 * 4 加密
 * 散列算法   MD5   SHA   不可逆
 * 1> 配置类中
 * 1.1 注册
 * 2> 注册跟修改密码
 * session的配置
 * 缓存配置
 * 认证对象可以缓存
 * 权限缓存
 * 在自定义realm设置
 * 前后端分离
 * session中
 * 前后端分离
 * 1 ajax发送登录的请求  用户名密码
 * 2 后台接受登录操作
 *   user 保存session中 保存用户对象
 * 3 登录完成  直接返回ssessionid
 * 4 ajax 获取到sessionId   123   localstaore
 * 5 前端发送任何ajax请求谢携带
 * 1.从localstroe 里获取sessId  获取到了
 *   ajax 携带sessionId 发送到后台
 * 2  如果没有获取到  未登录获取过期   前端跳转登录界面
 * 请求行
 * 请求头 (应该放在请求头中)   token: 123
 * 请求体  jwt   uid  + 过期时间 + + 加密
 *
 * 登录方法 返回jsessionId
 * spring security
 *
 */
@Configuration
public class ShiroConfig {
    public static final int HASH_ITERATIONS = 1024;
    // 注册自定义的Realm
    @Bean
    public UserRealm realm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        // 开启授权认证缓存
        userRealm.setCachingEnabled(true);
        // 开启授权缓存
        userRealm.setAuthorizationCachingEnabled(true);
        //  设置认证缓存
        userRealm.setAuthenticationCachingEnabled(true);

        return userRealm;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm, SessionManager sessionManager, RedisCacheManager redisCacheManagerr) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //1 .设置自定的Realm
        defaultWebSecurityManager.setRealm(userRealm);
        //2. 配置 ssesionmanger
        defaultWebSecurityManager.setSessionManager(sessionManager);
        //3. 设置 缓存管理
        defaultWebSecurityManager.setCacheManager(redisCacheManagerr);
        return defaultWebSecurityManager;
    }
    /**
     * 加过密
     */
//    cookie + session  前后端分离
//     token  jwt

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
        definition.addPathDefinition("/test","anon");

        // 用户登出的操作
        definition.addPathDefinition("/logout", "logout");
        // 购物车
        definition.addPathDefinition("/cart/**", "authc");
        // authc 表示所有的api开头的url地址必须登录 403
        definition.addPathDefinition("/api/**", "authc");
        definition.addPathDefinition("/admin/**", "authc");
        return definition;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 使用md5密码匹配器
        //  matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        //  使用SHA256 密码匹配器
        matcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        //设置加密次数
        matcher.setHashIterations(HASH_ITERATIONS);
        // 如果启用了注意注册和更新密码的时候使用 toHex方法
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * 配置监听
     * RedisSessionDao
     *
     * @return
     */
    @Bean
    public CustomDefaultWebSessionManger sessionManager(ShiroSessionListener shiroSessionListener, RedisSessionDAO sessionDao) {
        CustomDefaultWebSessionManger sessionManager = new CustomDefaultWebSessionManger();
        // 设置监听 例如: 统计网站在线人数
        List<SessionListener> listeners = new ArrayList<>();
        listeners.add(shiroSessionListener);
        // 1 配置监听
        sessionManager.setSessionListeners(listeners);
        // 分布式
        // 2 设置将session信息保存到redis
        sessionManager.setSessionDAO(sessionDao);
        //禁用cookie
        sessionManager.setSessionIdCookieEnabled(false);
        // 禁止重写url携带cookie信息
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 默认1小时过期
//        sessionManager.setGlobalSessionTimeout();
        return sessionManager;
    }

    /**
     * 将session存储到redis中
     *
     * @return
     */
    @Bean
    public RedisSessionDAO sessionDao(CustomRedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //必须依赖于redisManager
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /**
     * 配置信息  需要自定义RedisManager
     *
     * @return
     */
    @Bean
    public CustomRedisManager redisManager(JedisProperties jedisProperties) {
        CustomRedisManager redisManager = new CustomRedisManager(jedisProperties);
        return redisManager;
    }

    /**
     * 自定义sessionId生成器
     *
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public ShiroSessionListener shiroSessionListener() {
        return new ShiroSessionListener();
    }


    //    shiro redis缓存配置
    @Bean
    public RedisCacheManager redisCacheManager(CustomRedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        //设置redismanager管理类
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

}
