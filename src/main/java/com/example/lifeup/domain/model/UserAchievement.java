package com.example.lifeup.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.lifeup.adapters.outbound.persistence.JpaAchievementEntity;
import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;

public class UserAchievement {
    private UUID id;
    private JpaUserEntity user;
    private JpaAchievementEntity achievement;
    private LocalDateTime unlockedAt;

    public UserAchievement(UUID id, JpaUserEntity user, JpaAchievementEntity achievement) {
        this.id = id;
        this.user = user;
        this.achievement = achievement;
        this.unlockedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public JpaUserEntity getUser() { return user; }
    public void setUser(JpaUserEntity user) { this.user = user; }

    public JpaAchievementEntity getAchievement() { return achievement; }
    public void setAchievement(JpaAchievementEntity achievement) { this.achievement = achievement; }

    public LocalDateTime getUnlockedAt() { return unlockedAt; }
    public void setUnlockedAt(LocalDateTime unlockedAt) { this.unlockedAt = unlockedAt; }
}
