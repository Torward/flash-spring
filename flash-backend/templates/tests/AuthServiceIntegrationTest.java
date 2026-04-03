package ru.lomov.flashbackend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.lomov.flashbackend.AbstractIntegrationTest;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for Auth Service.
 * Tests authentication, registration, and token management endpoints.
 */
class AuthServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should register new user successfully")
    void shouldRegisterNewUser() throws Exception {
        // Given
        Map<String, String> registrationRequest = Map.of(
            "username", "testuser",
            "email", "test@example.com",
            "password", "SecurePassword123!",
            "displayName", "Test User"
        );

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequest)));

        // Then
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").exists())
            .andExpect(jsonPath("$.user.username").value("testuser"))
            .andExpect(jsonPath("$.user.email").value("test@example.com"));
    }

    @Test
    @DisplayName("Should login with valid credentials")
    void shouldLoginWithValidCredentials() throws Exception {
        // Given
        Map<String, String> loginRequest = Map.of(
            "username", "existinguser",
            "password", "SecurePassword123!"
        );

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)));

        // Then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").exists())
            .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    @DisplayName("Should return 401 for invalid credentials")
    void shouldReturn401ForInvalidCredentials() throws Exception {
        // Given
        Map<String, String> loginRequest = Map.of(
            "username", "nonexistent",
            "password", "wrongpassword"
        );

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)));

        // Then
        result.andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Should refresh access token with valid refresh token")
    void shouldRefreshAccessToken() throws Exception {
        // Given - first login to get refresh token
        Map<String, String> loginRequest = Map.of(
            "username", "testuser",
            "password", "SecurePassword123!"
        );

        String refreshToken = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        // Extract refresh token from response (simplified - in real test parse JSON)
        // String refreshTokenValue = extractRefreshToken(refreshToken);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/auth/refresh")
            .header("Authorization", "Bearer " + refreshToken));

        // Then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @DisplayName("Should validate email format during registration")
    void shouldValidateEmailFormat() throws Exception {
        // Given
        Map<String, String> invalidRequest = Map.of(
            "username", "testuser",
            "email", "invalid-email",
            "password", "SecurePassword123!"
        );

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)));

        // Then
        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @DisplayName("Should validate password strength during registration")
    void shouldValidatePasswordStrength() throws Exception {
        // Given
        Map<String, String> weakPasswordRequest = Map.of(
            "username", "testuser",
            "email", "test@example.com",
            "password", "123"
        );

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(weakPasswordRequest)));

        // Then
        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").isArray());
    }
}
