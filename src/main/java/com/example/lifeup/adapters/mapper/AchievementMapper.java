package com.example.lifeup.adapters.mapper;

import org.springframework.stereotype.Component;

import com.example.lifeup.adapters.outbound.persistence.JpaAchievementEntity;
import com.example.lifeup.domain.model.Achievement;

@Component
public class AchievementMapper {
    public JpaAchievementEntity toEntity(Achievement achievement) {
        JpaAchievementEntity entity = new JpaAchievementEntity();
        entity.setId(achievement.getId());
        entity.setName(achievement.getName());
        entity.setDescription(achievement.getDescription());
        entity.setConditionType(achievement.getConditionType());
        entity.setConditionValue(achievement.getConditionValue());
        entity.setRewardType(achievement.getRewardType());
        entity.setRewardAmount(achievement.getRewardAmount());
        entity.setRewardItemId(achievement.getRewardItemId());
        return entity;
    }

    public Achievement toDomain(JpaAchievementEntity entity) {
        return new Achievement(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(), 
            entity.getConditionType(), 
            entity.getConditionValue(),
            entity.getRewardType(),
            entity.getRewardAmount(),
            entity.getRewardItemId()
        );
    }
}
