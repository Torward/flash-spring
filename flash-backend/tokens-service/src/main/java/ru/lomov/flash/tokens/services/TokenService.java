package ru.lomov.flash.tokens.services;

import ru.lomov.flash.tokens.entities.Token;
import ru.lomov.flash.tokens.dto.TokenRequest;

import java.util.List;
import java.util.Optional;

public interface TokenService {
    
    Token saveToken(String userId, TokenRequest tokenRequest);
    
    Optional<Token> getTokenByUserId(String userId);
    
    List<Token> getAllTokensByUserId(String userId);
    
    Optional<Token> getTokenByTokenValue(String token);
    
    boolean existsByUserIdAndToken(String userId, String token);
    
    void deleteToken(String userId, String token);
    
    void deleteAllTokensByUserId(String userId);
    
    void updateToken(String userId, TokenRequest tokenRequest);
}
