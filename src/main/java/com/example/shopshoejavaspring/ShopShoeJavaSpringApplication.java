package com.example.shopshoejavaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.shopshoejavaspring.*"})
@EntityScan("com.example.shopshoejavaspring.entity")
public class ShopShoeJavaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopShoeJavaSpringApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
}
