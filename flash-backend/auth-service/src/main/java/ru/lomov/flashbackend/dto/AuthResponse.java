package ru.lomov.flashbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String status;
    private String message;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private UserInfo user;
    
    public AuthResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
    

    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String roles;
    }
}
