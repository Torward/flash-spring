package ru.lomov.flash.reaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactionApplication.class, args);
    }
}
