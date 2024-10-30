package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
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
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.UserRepository;
import ru.lomov.flashbackend.responses.AuthResponse;
import ru.lomov.flashbackend.services.CustomUserDetailsServiceImplementation;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

    @PostMapping("/signup")
    public AuthResponse registerUser(@RequestBody AppUser user) throws UserNotFoundException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String username = lastName + "_" + firstName + "_" + LocalDateTime.now() + "." + email + "." + Math.random() * 10000;
        if (userRepository.findAppUserByUsername(username).isPresent()) {
            username = username + "_" + (long) (Math.random() * 10000L);
        }

        if (userRepository.findAppUserByEmail(email).isPresent()) {
            throw new UserNotFoundException("Адрес электронной " + email + " почты уже используется!");
        }
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
        return new AuthResponse(token, true);
    }
    @PostMapping("/signIn")
    public AuthResponse signIn(@RequestBody AppUser user) {
        if (userRepository.findAppUserByEmail(user.getEmail()).isEmpty()) {
            throw new UserNotFoundException("Пользователь с таким именем не найден!");
        }
        String username = user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(username, password);


        String token = jwtProvider.generateToken(authentication);
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
}
