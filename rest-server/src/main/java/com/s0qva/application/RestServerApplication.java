package com.s0qva.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServerApplication.class, args);
    }
}
