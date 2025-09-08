package com.example.lifeup.adapters.inbound.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lifeup.adapters.dto.UserAchievementDto;
import com.example.lifeup.adapters.mapper.UserAchievementMapper;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserAchievementRepository;
import com.example.lifeup.domain.model.Achievement;
import com.example.lifeup.domain.port.out.AchievementRepositoryPort;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {
    private final AchievementRepositoryPort achievementRepository;
    private final SpringDataUserAchievementRepository userAchievementRepository;

    public AchievementController(AchievementRepositoryPort achievementRepository,
                                 SpringDataUserAchievementRepository userAchievementRepository) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
    }

    // Todos los logros existentes
    @GetMapping
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    // Logros desbloqueados de un usuario
    @GetMapping("/user/{userId}")
    public List<UserAchievementDto> getUserAchievements(@PathVariable UUID userId) {
        return userAchievementRepository.findByUserId(userId)
                .stream()
                .map(UserAchievementMapper::toDto)
                .toList();
    }
}
