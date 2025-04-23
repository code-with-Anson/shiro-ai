package com.alice.shiroai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.alice.shiroai.mapper")
@SpringBootApplication
public class ShiroAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroAiApplication.class, args);
    }

}
