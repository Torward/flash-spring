package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lomov.flashbackend.config.JwtProvider;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Verification;
import ru.lomov.flashbackend.exceptions.UserAlreadyExistsException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.UserRepository;
import ru.lomov.flashbackend.responses.AuthResponse;
import ru.lomov.flashbackend.services.CustomUserDetailsServiceImplementation;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

    @PostMapping("/signup")
    public AuthResponse registerUser(@RequestBody AppUser user) throws UserAlreadyExistsException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if (userRepository.findAppUserByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Адрес электронной почты " + email + " уже используется!");
        }

        String username = generateUsername(firstName, lastName, email);
        AppUser createdUser = new AppUser();
        createdUser.setEmail(email);
        createdUser.setUsername(username);
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setFullName(lastName + " " + firstName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setVerification(Verification.builder().status(true).build());

        AppUser savedUser = userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        logger.info("User registered successfully: {}", email);
        return new AuthResponse(token, true);
    }

    @PostMapping("/signIn")
    public AuthResponse signIn(@RequestBody AppUser user) {
        String email = user.getEmail();
        String password = user.getPassword();

        AppUser existingUser = userRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с email " + email + " не найден!"));

        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Неверный пароль!");
        }

        Authentication authentication = authenticate(email, password);
        String token = jwtProvider.generateToken(authentication);

        logger.info("User signed in successfully: {}", email);
        return new AuthResponse(token, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsServiceImplementation.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Неверное имя пользователя!");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Неверные имя пользователя или пароль...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String generateUsername(String firstName, String lastName, String email) {
        String baseUsername = lastName + "_" + firstName + "_" + LocalDateTime.now().getYear();
        String username = baseUsername;
        int suffix = 1;
        while (userRepository.findAppUserByUsername(username).isPresent()) {
            username = baseUsername + "_" + suffix++;
        }
        return username;
    }
}