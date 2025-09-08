package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.lifeup.domain.model.Task;
import com.example.lifeup.domain.port.out.TaskRepositoryPort;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {
    private final SpringDataTaskRepository repository;

    public TaskRepositoryAdapter(SpringDataTaskRepository repository){
        this.repository = repository;
    }

    @Override
    public Task save(Task task) {
        JpaTaskEntity entity = toEntity(task);
        JpaTaskEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        System.out.println("🔎 [RepoAdapter] buscando tarea con id=" + id);
        return repository.findById(id)
                .map(task -> {
                    System.out.println("✅ [RepoAdapter] encontrada tarea: " + task.getId());
                    return toDomain(task);
                });
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Task> findByUser(JpaUserEntity user) {
        return repository.findByUser(user)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private JpaTaskEntity toEntity(Task task) {
        JpaTaskEntity entity = new JpaTaskEntity();
        entity.setId(task.getId());
        entity.setUser(task.getUser());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setDueDate(task.getDueDate());
        entity.setDone(task.isDone());
        entity.setPoints(task.getPoints());
        entity.setCreatedAt(task.getCreatedAt());
        entity.setDifficulty(task.getDifficulty());
        return entity;
    }

    private Task toDomain(JpaTaskEntity entity) {
        return new Task(
            entity.getId(), 
            entity.getUser(), 
            entity.getTitle(), 
            entity.getDescription(), 
            entity.getDueDate(), 
            entity.isDone(),
            entity.getPoints(),
            entity.getDifficulty()
        );
    }
}
