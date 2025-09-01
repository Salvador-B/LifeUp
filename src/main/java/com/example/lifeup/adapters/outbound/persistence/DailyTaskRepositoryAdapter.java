package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.lifeup.domain.model.DailyTask;
import com.example.lifeup.domain.port.out.DailyTaskRepositoryPort;

@Component
public class DailyTaskRepositoryAdapter implements DailyTaskRepositoryPort {
    private final SpringDataDailyTaskRepository repository;

    public DailyTaskRepositoryAdapter(SpringDataDailyTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public DailyTask save(DailyTask dailyTask) {
        JpaDailyTaskEntity entity = toEntity(dailyTask);
        JpaDailyTaskEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<DailyTask> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DailyTask> findById(UUID id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<DailyTask> findByUser(JpaUserEntity user) {
        return repository.findByUser(user)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private JpaDailyTaskEntity toEntity(DailyTask dailyTask) {
        JpaDailyTaskEntity entity = new JpaDailyTaskEntity();
        entity.setId(dailyTask.getId());
        entity.setUser(dailyTask.getUser());
        entity.setTitle(dailyTask.getTitle());
        entity.setDescription(dailyTask.getDescription());
        entity.setLastCompletedDate(dailyTask.getLastCompletedDate());
        entity.setDoneToday(dailyTask.isDoneToday());
        entity.setPoints(dailyTask.getPoints());
        entity.setDifficulty(dailyTask.getDifficulty());
        entity.setStartDate(dailyTask.getStartDate());
        entity.setRecurrenceType(dailyTask.getRecurrenceType());
        entity.setRepeatEvery(dailyTask.getRepeatEvery());
        entity.setDaysOfWeek(dailyTask.getDaysOfWeek());
        return entity;
    }

    private DailyTask toDomain(JpaDailyTaskEntity entity) {
        return new DailyTask(
            entity.getId(),
            entity.getUser(), 
            entity.getTitle(), 
            entity.getDescription(),  
            entity.isDoneToday(),
            entity.getLastCompletedDate(),
            entity.getPoints(),
            entity.getDifficulty(),
            entity.getStartDate(),
            entity.getRecurrenceType(),
            entity.getRepeatEvery(),
            entity.getDaysOfWeek()
        );
    }
}
