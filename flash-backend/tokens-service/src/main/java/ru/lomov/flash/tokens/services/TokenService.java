package ru.lomov.flash.tokens.services;

import ru.lomov.flash.tokens.dto.TokenResponse;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    // Firebase-compatible methods
    Optional<TokenResponse> getToken(String userId);

    TokenResponse saveToken(String userId, String fcmToken, String deviceType, String deviceId);

    TokenResponse updateToken(String userId, String fcmToken);

    boolean deleteToken(String userId);

    boolean deactivateToken(String userId, String deviceId);

    boolean deactivateAllTokens(String userId);

    List<TokenResponse> getActiveTokensByUser(String userId);

    List<TokenResponse> getAllActiveTokens();

    long getActiveTokenCount(String userId);

    boolean hasActiveToken(String userId);
}
