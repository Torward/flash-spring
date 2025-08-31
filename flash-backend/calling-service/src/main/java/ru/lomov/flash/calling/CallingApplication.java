package ru.lomov.flash.calling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CallingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallingApplication.class, args);
    }
}
