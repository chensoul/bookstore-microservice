package com.chensoul.bookstore.user;

import org.springframework.boot.SpringApplication;

public class TestProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(UserServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
