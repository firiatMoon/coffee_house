package com.capybara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class TGService {
    public static void main(String[] args) {
        SpringApplication.run(TGService.class, args);
    }
}