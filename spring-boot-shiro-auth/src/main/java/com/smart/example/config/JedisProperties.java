package com.smart.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class JedisProperties {
    private String host;
    private int port;
    private int database;
    private String password;
    private int timeout;
}
