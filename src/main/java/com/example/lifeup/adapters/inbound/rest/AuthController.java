package com.example.lifeup.adapters.inbound.rest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserRepository;
import com.example.lifeup.application.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SpringDataUserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthController(SpringDataUserRepository users, PasswordEncoder encoder,
                          AuthenticationManager authManager, JwtService jwt) {
        this.users = users; this.encoder = encoder; this.authManager = authManager; this.jwt = jwt;
    }

    public record RegisterRequest(String name, String email, String password){}
    public record LoginRequest(String email, String password){}
    public record UserDto(UUID id, String name, String email, int level, int experience, int coins, int health, int maxHealth){}
    public record AuthResponse(String token, UserDto user){}

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req){
        users.findByEmail(req.email()).ifPresent(u -> { throw new RuntimeException("Email en uso"); });
        JpaUserEntity u = new JpaUserEntity();
        u.setId(UUID.randomUUID());
        u.setName(req.name());
        u.setEmail(req.email());
        u.setPass(encoder.encode(req.password())); // HASH BCRYPT
        u.setLevel(1); u.setExperience(0); u.setHealth(100); u.setMaxHealth(100);
        u.setCoins(0); u.setStreakCount(0); u.setCreatedAt(LocalDateTime.now());
        users.save(u);

        String token = jwt.generate(u.getEmail(), Duration.ofDays(1));
        return new AuthResponse(token, toDto(u));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        JpaUserEntity u = users.findByEmail(req.email()).orElseThrow();
        String token = jwt.generate(u.getEmail(), Duration.ofDays(1));
        return new AuthResponse(token, toDto(u));
    }

    @GetMapping("/me")
    public UserDto me(Authentication auth){
        JpaUserEntity u = users.findByEmail(auth.getName()).orElseThrow();
        return toDto(u);
    }

    private UserDto toDto(JpaUserEntity u){
        return new UserDto(u.getId(), u.getName(), u.getEmail(), u.getLevel(), u.getExperience(), u.getCoins(), u.getHealth(), u.getMaxHealth());
    }
}
