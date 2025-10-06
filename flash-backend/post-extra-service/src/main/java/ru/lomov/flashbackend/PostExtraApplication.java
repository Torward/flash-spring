package ru.lomov.flashbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PostExtraApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostExtraApplication.class, args);
    }
}
