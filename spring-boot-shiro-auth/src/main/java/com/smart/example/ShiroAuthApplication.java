package com.smart.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.smart.example.mapper")
public class ShiroAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroAuthApplication.class,args);
    }
}
