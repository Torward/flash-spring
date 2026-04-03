package ru.lomov.flash.apidocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * API Documentation Service with SpringDoc OpenAPI.
 * 
 * This service aggregates OpenAPI documentation from all microservices
 * and provides a centralized Swagger UI interface.
 * 
 * Endpoints:
 * - GET /swagger-ui.html - Interactive API documentation
 * - GET /v3/api-docs - OpenAPI JSON specification
 * - GET /v3/api-docs/{service} - OpenAPI spec for specific service
 * 
 * @author Flash Team
 */
@SpringBootApplication
@EnableEurekaClient
public class ApiDocsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDocsServiceApplication.class, args);
    }
}
