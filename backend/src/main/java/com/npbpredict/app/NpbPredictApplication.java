package com.npbpredict.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NpbPredictApplication {

    public static void main(String[] args) {
        SpringApplication.run(NpbPredictApplication.class, args);
    }

}
