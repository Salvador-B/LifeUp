package com.example.lifeup.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.lifeup.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
}
