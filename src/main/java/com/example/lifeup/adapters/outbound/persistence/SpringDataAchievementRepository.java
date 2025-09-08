package com.example.lifeup.adapters.outbound.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAchievementRepository extends JpaRepository<JpaAchievementEntity, UUID> {
    
}
