package com.example.lifeup.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.domain.model.DailyTask;

public interface DailyTaskRepositoryPort {
    DailyTask save(DailyTask dailyTask);
    List<DailyTask> findAll();
    Optional<DailyTask> findById(UUID id);
    void deleteById(UUID id);
    List<DailyTask> findByUser(JpaUserEntity user);
}
