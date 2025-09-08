package com.example.lifeup.application.service;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lifeup.adapters.mapper.UserMapper;
import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserRepository;
import com.example.lifeup.domain.model.User;
import com.example.lifeup.domain.port.out.UserRepositoryPort;

@Service
public class UserService {
    private final UserRepositoryPort repository;
    private final SpringDataUserRepository springUserRepo;
    private final UserMapper mapper;

    public UserService(UserRepositoryPort repository, SpringDataUserRepository springUserRepo, UserMapper mapper) {
        this.repository = repository;
        this.springUserRepo = springUserRepo;
        this.mapper = mapper;
    }

    private JpaUserEntity currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return springUserRepo.findByEmail(email).orElseThrow();
    }

    public User createUser(String name, String email, String pass){
        User user = new User(UUID.randomUUID(), name, 0, 0, 100, 100, email, pass, 0, 0);
        User saved = repository.save(user);

        return saved;
    }

    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }

    public User getUser() {
        return mapper.toDomain(currentUser());
    }

    @Transactional
    public User addExperience(UUID userId, int points) {
        JpaUserEntity user = springUserRepo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

        user.setExperience(user.getExperience() + points);

        // si supera el umbral, sube de nivel
        int expNeeded = user.getLevel() * 20; // regla simple
        while (user.getExperience() >= expNeeded) {
            user.setExperience(user.getExperience() - expNeeded);
            user.setLevel(user.getLevel() + 1);
            expNeeded = user.getLevel() * 20;

            // recuperar algo de vida al subir de nivel
            user.setHealth(user.getMaxHealth());
        }

        JpaUserEntity saved = springUserRepo.save(user);
        return mapper.toDomain(saved);
    }
}
