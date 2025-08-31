package ru.lomov.flashbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ru.lomov.flashbackend.dto.AuthResponse;
import ru.lomov.flashbackend.dto.LoginRequest;
import ru.lomov.flashbackend.dto.RefreshTokenRequest;
import ru.lomov.flashbackend.dto.RegisterRequest;
import ru.lomov.flashbackend.entities.User;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        User user = userService.findByUsername(userDetails.getUsername());
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles()
        );
        AuthResponse response = new AuthResponse(
                "success",
                "Authentication successful",
                accessToken,
                refreshToken,
                86400000L, // 24 hours in milliseconds
                userInfo
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        
        // Validate refresh token
        try {
            String username = jwtUtil.extractUsernameFromRefreshToken(refreshToken);
            UserDetails userDetails = userService.loadUserByUsername(username);
            
            if (jwtUtil.validateRefreshToken(refreshToken, userDetails)) {
                String newAccessToken = jwtUtil.generateAccessToken(userDetails);
                String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
                
                User user = userService.findByUsername(username);
                AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getRoles()
                );
                AuthResponse response = new AuthResponse(
                        "success",
                        "Token refreshed successfully",
                        newAccessToken,
                        newRefreshToken,
                        86400000L, // 24 hours in milliseconds
                        userInfo
                );
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(new AuthResponse("error", "Invalid refresh token"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse("error", "Invalid refresh token"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User newUser = userService.registerUser(registerRequest);
            return ResponseEntity.ok(new AuthResponse("success", "User registered successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthResponse("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        // In a real implementation, you might want to blacklist the token
        return ResponseEntity.ok(new AuthResponse("success", "Logged out successfully"));
    }
}
