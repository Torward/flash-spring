package ru.lomov.flashbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration for Flash-Spring microservices.
 * Provides API documentation with SpringDoc OpenAPI.
 */
@Configuration
public class OpenApiConfig {

    @Value("${service.name:Flash Service}")
    private String serviceName;

    @Value("${service.description:Flash-Spring Microservice}")
    private String serviceDescription;

    @Value("${service.version:1.0.0}")
    private String serviceVersion;

    @Value("${server.url:http://localhost:8080}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(serviceName + " API")
                .version(serviceVersion)
                .description(serviceDescription)
                .contact(new Contact()
                    .name("Flash-Spring Team")
                    .email("support@flash-spring.ru")
                    .url("https://github.com/lomov/flash-spring"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url(serverUrl)
                    .description("Development server"),
                new Server()
                    .url("https://api.flash-spring.ru")
                    .description("Production server")
            ));
    }
}
