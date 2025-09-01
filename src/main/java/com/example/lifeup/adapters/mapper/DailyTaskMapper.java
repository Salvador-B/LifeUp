package com.example.lifeup.adapters.mapper;

import com.example.lifeup.adapters.dto.DailyTaskDto;
import com.example.lifeup.adapters.dto.UserDto;
import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.domain.model.DailyTask;

public class DailyTaskMapper {

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

    public static DailyTaskDto toDto(DailyTask task) {
        return new DailyTaskDto(
                task.getId(),
                toUserDto(task.getUser()),
                task.getTitle(),
                task.getDescription(),
                task.isDoneToday(),
                task.getLastCompletedDate(),
                task.getPoints(),
                task.getDifficulty(),
                task.getStartDate(),
                task.getRecurrenceType(),
                task.getRepeatEvery(),
                task.getDaysOfWeek()
        );
    }
}
