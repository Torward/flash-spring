package ru.lomov.flash.tokens.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.tokens.dto.TokenRequest;
import ru.lomov.flash.tokens.entities.Token;
import ru.lomov.flash.tokens.repositories.TokenRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Token saveToken(String userId, TokenRequest tokenRequest) {
        // Check if token already exists for this user and device
        Optional<Token> existingToken = tokenRepository.findByUserIdAndDeviceId(userId, tokenRequest.getDeviceId());
        
        if (existingToken.isPresent()) {
            // Update existing token
            Token token = existingToken.get();
            token.setToken(tokenRequest.getToken());
            return tokenRepository.save(token);
        } else {
            // Create new token
            Token token = new Token();
            token.setUserId(userId);
            token.setToken(tokenRequest.getToken());
            token.setDeviceId(tokenRequest.getDeviceId());
            return tokenRepository.save(token);
        }
    }

    @Override
    public Optional<Token> getTokenByUserId(String userId) {
        List<Token> tokens = tokenRepository.findByUserId(userId);
        return tokens.stream().findFirst();
    }

    @Override
    public List<Token> getAllTokensByUserId(String userId) {
        return tokenRepository.findByUserId(userId);
    }

    @Override
    public Optional<Token> getTokenByTokenValue(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public boolean existsByUserIdAndToken(String userId, String token) {
        return tokenRepository.existsByUserIdAndToken(userId, token);
    }

    @Override
    public void deleteToken(String userId, String token) {
        tokenRepository.deleteByUserIdAndToken(userId, token);
    }

    @Override
    public void deleteAllTokensByUserId(String userId) {
        tokenRepository.deleteAllByUserId(userId);
    }

    @Override
    public void updateToken(String userId, TokenRequest tokenRequest) {
        Optional<Token> existingToken = tokenRepository.findByUserIdAndDeviceId(userId, tokenRequest.getDeviceId());
        
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setToken(tokenRequest.getToken());
            tokenRepository.save(token);
        } else {
            saveToken(userId, tokenRequest);
        }
    }
}
