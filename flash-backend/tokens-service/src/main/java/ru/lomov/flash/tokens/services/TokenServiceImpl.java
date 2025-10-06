package ru.lomov.flash.tokens.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flash.tokens.dto.TokenResponse;
import ru.lomov.flash.tokens.entities.Token;
import ru.lomov.flash.tokens.repositories.TokenRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<TokenResponse> getToken(String userId) {
        return tokenRepository.findByUserId(userId)
                .map(this::mapToResponse);
    }

    @Override
    public TokenResponse saveToken(String userId, String fcmToken, String deviceType, String deviceId) {
        // Deactivate existing token for this user/device combination
        tokenRepository.deactivateTokenByUserAndDevice(userId, deviceId);

        Token token = Token.builder()
                .userId(userId)
                .fcmToken(fcmToken)
                .deviceType(deviceType)
                .deviceId(deviceId)
                .isActive(true)
                .build();

        Token savedToken = tokenRepository.save(token);
        return mapToResponse(savedToken);
    }

    @Override
    public TokenResponse updateToken(String userId, String fcmToken) {
        Token token = tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Token not found for user: " + userId));

        token.setFcmToken(fcmToken);
        Token updatedToken = tokenRepository.save(token);
        return mapToResponse(updatedToken);
    }

    @Override
    public boolean deleteToken(String userId) {
        Optional<Token> token = tokenRepository.findByUserId(userId);
        if (token.isPresent()) {
            tokenRepository.delete(token.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deactivateToken(String userId, String deviceId) {
        int updated = tokenRepository.deactivateTokenByUserAndDevice(userId, deviceId);
        return updated > 0;
    }

    @Override
    public boolean deactivateAllTokens(String userId) {
        int updated = tokenRepository.deactivateAllTokensByUser(userId);
        return updated > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TokenResponse> getActiveTokensByUser(String userId) {
        return tokenRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TokenResponse> getAllActiveTokens() {
        return tokenRepository.findAllActiveTokens()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getActiveTokenCount(String userId) {
        return tokenRepository.countActiveTokensByUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasActiveToken(String userId) {
        return tokenRepository.countActiveTokensByUser(userId) > 0;
    }

    private TokenResponse mapToResponse(Token token) {
        return TokenResponse.builder()
                .userId(token.getUserId())
                .fcmToken(token.getFcmToken())
                .deviceType(token.getDeviceType())
                .deviceId(token.getDeviceId())
                .createdAt(token.getCreatedAt())
                .updatedAt(token.getUpdatedAt())
                .isActive(token.getIsActive())
                .build();
    }
}
