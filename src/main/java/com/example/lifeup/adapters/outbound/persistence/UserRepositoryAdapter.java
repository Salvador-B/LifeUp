package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.lifeup.adapters.mapper.UserMapper;
import com.example.lifeup.domain.model.User;
import com.example.lifeup.domain.port.out.UserRepositoryPort;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository repository;
    private final UserMapper mapper;

    public UserRepositoryAdapter(SpringDataUserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        JpaUserEntity entity = mapper.toEntity(user);
        JpaUserEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
