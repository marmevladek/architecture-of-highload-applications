package ru.dating.app.swipeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SwipeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwipeServiceApplication.class, args);
    }
}
