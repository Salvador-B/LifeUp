package com.example.lifeup.adapters.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.lifeup.domain.model.TaskDifficulty;

public record TaskDto(
    UUID id,
    UserDto user,
    String title,
    String description,
    LocalDate dueDate,
    boolean done,
    int points,
    LocalDateTime createdAt,
    TaskDifficulty difficulty
) {}