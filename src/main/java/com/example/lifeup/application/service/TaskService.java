package com.example.lifeup.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserRepository;
import com.example.lifeup.domain.model.Task;
import com.example.lifeup.domain.model.TaskDifficulty;
import com.example.lifeup.domain.port.out.TaskRepositoryPort;
import com.example.lifeup.domain.port.out.UserRepositoryPort;

@Service
public class TaskService {
    private final TaskRepositoryPort repository;
    private final UserRepositoryPort userRepository;

    @Autowired
    private UserService userService;

    @Autowired 
    private SpringDataUserRepository springUserRepo;

    public TaskService(TaskRepositoryPort repository, UserRepositoryPort userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private JpaUserEntity currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return springUserRepo.findByEmail(email).orElseThrow();
    }

    public Task createTask(String title, String description, LocalDate dueDate, TaskDifficulty difficulty){
        int points = calculatePoints(difficulty);
        JpaUserEntity jpaUser = currentUser();
        Task task = new Task(UUID.randomUUID(), jpaUser, title, description, dueDate, false, points, difficulty);
        return repository.save(task);
    }

    public List<Task> findAll(){
        JpaUserEntity jpaUser = currentUser();
        return repository.findByUser(jpaUser);
    }

    public Task updateTask(UUID id, Task updatedTask) {
        return repository.findById(id)
            .map(existingTask -> {
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setDueDate(updatedTask.getDueDate());
                existingTask.setDifficulty(updatedTask.getDifficulty());
                existingTask.setPoints(calculatePoints(updatedTask.getDifficulty()));
                return repository.save(existingTask);
            })
            .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    public void deleteTask(UUID id) {
        repository.deleteById(id);
    }

    public Task completeTask(UUID id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        if (task.isDone()) {
            throw new RuntimeException("Task already completed");
        }

        task.setDone(true);
        userService.addExperience(task.getPoints());
        return repository.save(task);
    }

    private int calculatePoints(TaskDifficulty difficulty) {
        switch (difficulty) {
            case FACIL: return 10;
            case NORMAL: return 20;
            case DIFICIL: return 40;
            case MUY_DIFICIL: return 60;
            default: return 0;
        }
    }
}
