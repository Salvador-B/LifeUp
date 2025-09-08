package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lifeup.domain.model.UserAchievement;

public interface SpringDataUserAchievementRepository extends JpaRepository<JpaUserAchievementEntity, UUID> {
    Optional<UserAchievement> findByUserIdAndAchievementId(UUID userId, UUID achievementId);
    List<UserAchievement> findByUserId(UUID userId);
}
