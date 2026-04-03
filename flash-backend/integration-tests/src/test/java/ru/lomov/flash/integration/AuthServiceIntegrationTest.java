package ru.lomov.flash.integration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration test for Auth Service.
 * 
 * Tests authentication endpoints:
 * - POST /api/auth/register - User registration
 * - POST /api/auth/login - User login
 * - POST /api/auth/refresh - Token refresh
 * - POST /api/auth/logout - User logout
 * 
 * @author Flash Team
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthServiceIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private Integer port;

    private RequestSpecification requestSpec;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        
        requestSpec = new RequestSpecBuilder()
            .setBasePath("/api/auth")
            .log(LogDetail.ALL)
            .build();
    }

    // TODO: Add actual test methods when auth-service is integrated
    // Example test structure:
    /*
    @Test
    @DisplayName("Should register new user successfully")
    void shouldRegisterNewUser() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("SecurePassword123!");
        
        given()
            .spec(requestSpec)
            .contentType(ContentType.JSON)
            .body(registerRequest)
        .when()
            .post("/register")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("username", equalTo("testuser"))
            .body("email", equalTo("test@example.com"));
    }
    
    @Test
    @DisplayName("Should login with valid credentials")
    void shouldLoginWithValidCredentials() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("SecurePassword123!");
        
        given()
            .spec(requestSpec)
            .contentType(ContentType.JSON)
            .body(loginRequest)
        .when()
            .post("/login")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("accessToken", notNullValue())
            .body("refreshToken", notNullValue());
    }
    */
}
