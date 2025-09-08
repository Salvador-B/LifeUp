package com.example.lifeup.domain.port.out;

import com.example.lifeup.domain.model.UserAchievement;

public interface UserAchievementRepositoryPort {
    UserAchievement save(UserAchievement userAchievement);
}
