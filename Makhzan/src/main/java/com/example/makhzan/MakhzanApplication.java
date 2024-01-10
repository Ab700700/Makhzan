package com.example.makhzan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MakhzanApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakhzanApplication.class, args);
    }

}
