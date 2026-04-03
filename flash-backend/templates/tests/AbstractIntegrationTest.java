package ru.lomov.flashbackend;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Abstract base class for integration tests in Flash-Spring microservices.
 * Provides Testcontainers PostgreSQL database and MockMvc for API testing.
 * 
 * Usage:
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * @ActiveProfiles("test")
 * public class YourServiceIntegrationTest extends AbstractIntegrationTest {
 *     // Your test methods here
 * }
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractIntegrationTest {

    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER = 
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
            .withDatabaseName("flash_test")
            .withUsername("test")
            .withPassword("test");

    static {
        POSTGRES_CONTAINER.start();
        System.setProperty("spring.datasource.url", POSTGRES_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES_CONTAINER.getPassword());
    }

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Common setup logic for all integration tests
        // Override in subclasses if needed
    }
}
