package ru.dating.app.deckservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class DeckServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeckServiceApplication.class, args);
    }
}
