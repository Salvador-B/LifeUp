package com.example.lifeup.domain.model;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class TaskCompletedEvent extends ApplicationEvent {
    private final UUID userId;
    private final UUID taskId;
    private final int points;

    public TaskCompletedEvent(Object source, UUID userId, UUID taskId, int points) {
        super(source);
        this.userId = userId;
        this.taskId = taskId;
        this.points = points;
    }

    public UUID getUserId() { return userId; }
    public UUID getTaskId() { return taskId; }
    public int getPoints() { return points; }
}
