package ru.lomov.flash.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Base class for integration tests with PostgreSQL Testcontainer.
 * 
 * Provides a shared PostgreSQL container for all integration tests.
 * Uses Testcontainers JUnit 5 extension for automatic lifecycle management.
 * 
 * @author Flash Team
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER = 
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
            .withDatabaseName("flash_test")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        POSTGRES_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", 
            () -> "org.postgresql.Driver");
    }
}
