package com.example.lifeup.domain.model;

import java.util.UUID;

public class Achievement {
    private UUID id;
    private String name;
    private String description;
    private AchievementConditionType conditionType;
    private int conditionValue;
    private AchievementRewardType rewardType;
    private int rewardAmount;
    private String rewardItemId;

    public Achievement(UUID id, String name, String description, AchievementConditionType conditionType, int conditionValue, AchievementRewardType rewardType, int rewardAmount, String rewardItemId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
        this.rewardType = rewardType;
        this.rewardAmount = rewardAmount;
        this.rewardItemId = rewardItemId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public AchievementConditionType getConditionType() { return conditionType; }
    public void setConditionType(AchievementConditionType conditionType) { this.conditionType = conditionType; }

    public int getConditionValue() { return conditionValue; }
    public void setConditionValue(int conditionValue) { this.conditionValue = conditionValue; }

    public AchievementRewardType getRewardType() { return rewardType; }
    public void setRewardType(AchievementRewardType rewardType) { this.rewardType = rewardType; }

    public int getRewardAmount() { return rewardAmount; }
    public void setRewardAmount(int rewardAmount) { this.rewardAmount = rewardAmount; }

    public String getRewardItemId() { return rewardItemId; }
    public void setRewardItemId(String rewardItemId) { this.rewardItemId = rewardItemId; }
}
