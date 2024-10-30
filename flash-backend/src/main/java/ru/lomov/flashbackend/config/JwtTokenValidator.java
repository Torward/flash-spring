package ru.lomov.flashbackend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        if (jwt != null) {
            jwt = jwt.substring(7);
            try {
                if (isTokenExpired(jwt)) {
                    throw new BadCredentialsException("Ваша сессия истекла, пожалуйста, авторизируйтесь снова.");
                }
                Authentication authentication = validateToken(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UnsupportedJwtException | MalformedJwtException | SecurityException |
                     IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
                throw new BadCredentialsException("Недопустимые данные для входа, пожалуйста, попробуйте еще раз.");
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenExpired(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return claimsJws.getPayload().getExpiration().before(new java.util.Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Authentication validateToken(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            String email = String.valueOf(claims.get("email"));
            String authorities = String.valueOf(claims.get("authorities"));
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            return new UsernamePasswordAuthenticationToken(email, null, auths);
        } catch (Exception e) {
            throw new BadCredentialsException("Недопустимые данные для входа, пожалуйста, попробуйте еще раз.");
        }
    }
}