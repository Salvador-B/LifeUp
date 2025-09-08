package com.example.lifeup.adapters.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserAchievementDto(
    UUID id,
    String name,
    String description,
    LocalDateTime unlockedAt
) {}
