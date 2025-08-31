package ru.lomov.flashbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createAccessToken(claims, userDetails.getUsername());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userDetails.getUsername());
    }

    private String createAccessToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, refreshTokenSecret)
                .compact();
    }

    public Boolean validateAccessToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromAccessToken(token);
        return (username.equals(userDetails.getUsername()) && !isAccessTokenExpired(token));
    }

    public Boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromRefreshToken(token);
        return (username.equals(userDetails.getUsername()) && !isRefreshTokenExpired(token));
    }

    public String extractUsernameFromAccessToken(String token) {
        return extractClaimFromAccessToken(token, Claims::getSubject);
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractClaimFromRefreshToken(token, Claims::getSubject);
    }

    public Date extractExpirationFromAccessToken(String token) {
        return extractClaimFromAccessToken(token, Claims::getExpiration);
    }

    public Date extractExpirationFromRefreshToken(String token) {
        return extractClaimFromRefreshToken(token, Claims::getExpiration);
    }

    public <T> T extractClaimFromAccessToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsFromAccessToken(token);
        return claimsResolver.apply(claims);
    }

    public <T> T extractClaimFromRefreshToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsFromRefreshToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaimsFromAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims extractAllClaimsFromRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(refreshTokenSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isAccessTokenExpired(String token) {
        try {
            return extractExpirationFromAccessToken(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Boolean isRefreshTokenExpired(String token) {
        try {
            return extractExpirationFromRefreshToken(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    // Backward compatibility methods
    public String generateToken(UserDetails userDetails) {
        return generateAccessToken(userDetails);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        return validateAccessToken(token, userDetails);
    }

    public String extractUsername(String token) {
        return extractUsernameFromAccessToken(token);
    }

    public Date extractExpiration(String token) {
        return extractExpirationFromAccessToken(token);
    }
}
