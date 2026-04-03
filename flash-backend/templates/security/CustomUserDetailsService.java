package ru.lomov.flashbackend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Custom UserDetailsService for loading user-specific data.
 * Implement this interface in each service that needs authentication.
 */
public interface CustomUserDetailsService extends UserDetailsService {
    
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
