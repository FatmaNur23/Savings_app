package org.parabiriktirme.savingsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ParaBiriktirmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParaBiriktirmeApplication.class, args);
    }
}