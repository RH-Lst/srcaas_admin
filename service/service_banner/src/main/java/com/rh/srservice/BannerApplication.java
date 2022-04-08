package com.rh.srservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rh")
@MapperScan("com.rh.srservice.mapper")
public class BannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BannerApplication.class,args);
    }
}
