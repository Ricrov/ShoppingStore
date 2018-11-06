package com.store.dev.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigSpringApplication.class, args);
    }
}
