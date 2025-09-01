package com.example.lifeup.adapters.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.lifeup.domain.model.RecurrenceType;
import com.example.lifeup.domain.model.TaskDifficulty;

public record DailyTaskDto(
        UUID id,
        UserDto user,
        String title,
        String description,
        boolean doneToday,
        LocalDate lastCompletedDate,
        int points,
        TaskDifficulty difficulty,
        LocalDate startDate,
        RecurrenceType recurrenceType,
        int repeatEvery,
        List<DayOfWeek> daysOfWeek
) {}
