package com.example.lifeup.adapters.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        int level,
        int experience,
        int health,
        int maxHealth,
        String email,
        String pass,
        int coins,
        int streakCount,
        LocalDateTime createdAt
) {}
