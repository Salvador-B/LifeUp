package com.example.lifeup.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.lifeup.domain.model.Achievement;

public interface AchievementRepositoryPort {
    Achievement save(Achievement achievement);
    List<Achievement> findAll();
    Optional<Achievement> findById(UUID id);
    void deleteById(UUID id);
}
