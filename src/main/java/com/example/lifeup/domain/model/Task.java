package com.example.lifeup.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;

public class Task {
    private UUID id;
    private JpaUserEntity user;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean done;
    private int points;
    private LocalDateTime createdAt;
    private TaskDifficulty difficulty;

    public Task(UUID id, JpaUserEntity user, String title, String description, LocalDate dueDate, boolean done, int points, TaskDifficulty difficulty){
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.done = done;
        this.points = points;
        this.createdAt = LocalDateTime.now();
        this.difficulty = difficulty;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public JpaUserEntity getUser() { return user; }
    public void setUser(JpaUserEntity user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public TaskDifficulty getDifficulty() { return difficulty; }
    public void setDifficulty(TaskDifficulty difficulty) { this.difficulty = difficulty; }
}