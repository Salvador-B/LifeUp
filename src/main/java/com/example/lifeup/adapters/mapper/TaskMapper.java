package com.example.lifeup.adapters.mapper;

import com.example.lifeup.adapters.dto.TaskDto;
import com.example.lifeup.adapters.dto.UserDto;
import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.domain.model.Task;

public class TaskMapper {
    private static UserDto toUserDto(JpaUserEntity user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLevel(),
                user.getExperience(),
                user.getHealth(),
                user.getMaxHealth(),
                user.getEmail(),
                user.getPass(),
                user.getCoins(),
                user.getStreakCount(),
                user.getCreatedAt()
        );
    }

    public static TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                toUserDto(task.getUser()),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.isDone(),
                task.getPoints(),
                task.getCreatedAt(),
                task.getDifficulty()
        );
    }
}
