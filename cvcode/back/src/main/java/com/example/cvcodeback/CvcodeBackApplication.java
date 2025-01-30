package com.example.cvcodeback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.cvcodeback.dao")
public class CvcodeBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CvcodeBackApplication.class, args);
    }

}
