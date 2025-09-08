package com.example.lifeup.adapters.mapper;

import com.example.lifeup.adapters.dto.UserAchievementDto;
import com.example.lifeup.domain.model.UserAchievement;

public class UserAchievementMapper {
    public static UserAchievementDto toDto(UserAchievement ua) {
        return new UserAchievementDto(
            ua.getId(),
            ua.getAchievement().getName(),
            ua.getAchievement().getDescription(),
            ua.getUnlockedAt()
        );
    }
}
