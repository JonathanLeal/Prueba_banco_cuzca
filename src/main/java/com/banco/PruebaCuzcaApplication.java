package com.banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
@ConfigurationPropertiesScan
public class PruebaCuzcaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebaCuzcaApplication.class, args);
    }

}