package com.example.lifeup.adapters.outbound.persistence;

import org.springframework.stereotype.Component;

import com.example.lifeup.domain.model.UserAchievement;
import com.example.lifeup.domain.port.out.UserAchievementRepositoryPort;

@Component
public class UserAchievementRepositoryAdapter implements UserAchievementRepositoryPort {
    private final SpringDataUserAchievementRepository repository;

    public UserAchievementRepositoryAdapter(SpringDataUserAchievementRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserAchievement save(UserAchievement userAchievement) {
        JpaUserAchievementEntity entity = toEntity(userAchievement);
        JpaUserAchievementEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    private JpaUserAchievementEntity toEntity(UserAchievement userAchievement) {
        JpaUserAchievementEntity entity = new JpaUserAchievementEntity();
        entity.setId(userAchievement.getId());
        entity.setUser(userAchievement.getUser());
        entity.setAchievement(userAchievement.getAchievement());
        entity.setUnlockedAt(userAchievement.getUnlockedAt());
        return entity;
    }

    private UserAchievement toDomain(JpaUserAchievementEntity entity) {
        return new UserAchievement(
            entity.getId(), 
            entity.getUser(), 
            entity.getAchievement()
        );
    }
}
