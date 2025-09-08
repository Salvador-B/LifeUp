package com.example.lifeup.application.service;

import org.springframework.stereotype.Service;

import com.example.lifeup.domain.port.out.UserAchievementRepositoryPort;

@Service
public class UserAchievementService {
    private final UserAchievementRepositoryPort repository;

    public UserAchievementService(UserAchievementRepositoryPort repository) {
        this.repository = repository;
    }
}
