package com.rh.calservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rh")
@MapperScan("com.rh.calservice.mapper")
public class CalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalApplication.class,args);
    }

}
