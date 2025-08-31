package ru.lomov.flash.ads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdsApplication.class, args);
    }
}
