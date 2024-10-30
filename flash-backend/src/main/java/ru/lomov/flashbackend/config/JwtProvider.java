package ru.lomov.flashbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.services.CustomUserDetailsServiceImplementation;

import javax.crypto.SecretKey;
import java.util.Date;
@RequiredArgsConstructor
@Service
public class JwtProvider {
    private final CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;
    SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){
        String jwt = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .claim("email", authentication.getName())
                .signWith(secretKey)
                .compact();
        return jwt;
    }
    public String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
        String email = String.valueOf(claims.get("email"));
        return email;
    }
    public boolean validateToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
        Date expiration = claims.getExpiration();
        Date now = new Date(System.currentTimeMillis());
        return expiration.after(now);
    }
    public boolean validateToken(String jwt, String email){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
        String emailFromToken = String.valueOf(claims.get("email"));
        return emailFromToken.equals(email);
    }
    public String refreshToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
        String email = String.valueOf(claims.get("email"));
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .claim("email", email)
                .signWith(secretKey)
                .compact();
    }
    public String getTokenByEmail(String email, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);;
        return generateToken(authentication);
    }

}