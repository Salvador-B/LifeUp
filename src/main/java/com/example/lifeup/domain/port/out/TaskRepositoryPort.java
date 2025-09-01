package com.example.lifeup.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.domain.model.Task;

public interface TaskRepositoryPort {
    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(UUID id);
    void deleteById(UUID id);
    List<Task> findByUser(JpaUserEntity user);
}
