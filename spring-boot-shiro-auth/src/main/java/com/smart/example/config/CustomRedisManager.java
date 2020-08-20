package com.smart.example.config;

import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.WorkAloneRedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CustomRedisManager extends WorkAloneRedisManager implements IRedisManager {
    JedisProperties jedisProperties;
    private JedisPool jedisPool;

    public CustomRedisManager(JedisProperties jedisProperties) {
        this.jedisProperties = jedisProperties;
    }

    private void init(){
        synchronized (this){
            if (this.jedisPool == null){
                this.jedisPool = new JedisPool(this.getJedisPoolConfig(),jedisProperties.getHost(),jedisProperties.getPort(),jedisProperties.getTimeout(),jedisProperties.getPassword(),jedisProperties.getDatabase());
            }
        }
    }


    @Override
    protected Jedis getJedis() {
        if (this.jedisPool == null){
            this.init();
        }
        return this.jedisPool.getResource();
    }
}
