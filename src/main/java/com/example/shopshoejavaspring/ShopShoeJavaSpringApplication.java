package com.example.shopshoejavaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.shopshoejavaspring.*"})
@EntityScan("com.example.shopshoejavaspring.entity")
public class ShopShoeJavaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopShoeJavaSpringApplication.class, args);
    }

}
