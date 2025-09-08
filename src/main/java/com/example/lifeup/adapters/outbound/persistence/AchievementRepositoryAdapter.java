package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.lifeup.adapters.mapper.AchievementMapper;
import com.example.lifeup.domain.model.Achievement;
import com.example.lifeup.domain.port.out.AchievementRepositoryPort;

@Component
public class AchievementRepositoryAdapter implements AchievementRepositoryPort {
    private final SpringDataAchievementRepository repository;
    private final AchievementMapper mapper;

    public AchievementRepositoryAdapter(SpringDataAchievementRepository repository, AchievementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Achievement save(Achievement achievement) {
        JpaAchievementEntity entity = mapper.toEntity(achievement);
        JpaAchievementEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Achievement> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Achievement> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
