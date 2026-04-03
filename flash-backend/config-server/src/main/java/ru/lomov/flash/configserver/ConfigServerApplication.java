package ru.lomov.flash.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Spring Cloud Config Server for centralized configuration management.
 * 
 * This server provides externalized configuration to all microservices
 * via HTTP endpoints. Configuration files are stored in a Git repository
 * or local filesystem.
 * 
 * Endpoints:
 * - GET /{application}/{profile}/{label} - Get configuration
 * - GET /{application}-{profile}.yml - Get YAML configuration
 * - GET /{application}-{profile}.properties - Get properties configuration
 * - POST /actuator/refresh - Refresh configuration (with Spring Cloud Bus)
 * 
 * @author Flash Team
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
