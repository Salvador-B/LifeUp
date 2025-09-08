package com.example.lifeup.adapters.outbound.persistence;

import java.util.UUID;

import com.example.lifeup.domain.model.AchievementConditionType;
import com.example.lifeup.domain.model.AchievementRewardType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class JpaAchievementEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AchievementConditionType conditionType;
    private int conditionValue;
    @Enumerated(EnumType.STRING)
    private AchievementRewardType rewardType;
    private int rewardAmount;
    private String rewardItemId;

    public JpaAchievementEntity() {}
}
