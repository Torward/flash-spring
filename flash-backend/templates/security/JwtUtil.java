package ru.lomov.flashbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Utility class for token generation and validation.
 * Configure JWT secrets in application.yml or Config Server.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret:mySecretKeyForJWTGeneration1234567890abcdefghijklmnopqrstuvwxyz}")
    private String secret;

    @Value("${jwt.access-token-expiration:86400000}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration:2592000000}")
    private long refreshTokenExpiration;

    @Value("${jwt.refresh-token-secret:myRefreshTokenSecretKey1234567890abcdefghijklmnopqrstuvwxyz123456}")
    private String refreshSecret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshSigningKey() {
        byte[] keyBytes = refreshSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), accessTokenExpiration, getSigningKey());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), refreshTokenExpiration, getRefreshSigningKey());
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration, SecretKey key) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public Boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String username = extractRefreshTokenUsername(token);
        return (username.equals(userDetails.getUsername())) && !isRefreshTokenExpired(token);
    }

    private String extractRefreshTokenUsername(String token) {
        final Claims claims = Jwts.parserBuilder()
            .setSigningKey(getRefreshSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    private Boolean isRefreshTokenExpired(String token) {
        final Claims claims = Jwts.parserBuilder()
            .setSigningKey(getRefreshSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getExpiration().before(new Date());
    }
}
