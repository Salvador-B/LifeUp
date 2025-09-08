package com.example.lifeup.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lifeup.adapters.mapper.AchievementMapper;
import com.example.lifeup.adapters.mapper.UserMapper;
import com.example.lifeup.adapters.outbound.persistence.SpringDataTaskRepository;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserAchievementRepository;
import com.example.lifeup.domain.model.Achievement;
import com.example.lifeup.domain.model.User;
import com.example.lifeup.domain.model.UserAchievement;
import com.example.lifeup.domain.port.out.AchievementRepositoryPort;
import com.example.lifeup.domain.port.out.UserAchievementRepositoryPort;
import com.example.lifeup.domain.port.out.UserRepositoryPort;

import jakarta.transaction.Transactional;

@Service
public class AchievementService {
    private final UserRepositoryPort userRepository;
    private final AchievementRepositoryPort achievementRepository;
    private final UserAchievementRepositoryPort userAchievementRepositoryPort;
    private final SpringDataUserAchievementRepository userAchievementRepository;
    private final SpringDataTaskRepository taskRepository;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private UserMapper userMapper;

    public AchievementService(UserRepositoryPort userRepository, AchievementRepositoryPort achievementRepository, SpringDataUserAchievementRepository userAchievementRepository, SpringDataTaskRepository taskRepository, UserAchievementRepositoryPort userAchievementRepositoryPort) {
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.taskRepository = taskRepository;
        this.userAchievementRepositoryPort = userAchievementRepositoryPort;
    }

    @Transactional
    public boolean unlockAchievement(UUID userId, UUID achievementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        // Evitar duplicados
        if (userAchievementRepository.findByUserIdAndAchievementId(userId, achievementId).isPresent()) {
            return false; // Ya lo tiene
        }

        // Crear relación
        UserAchievement ua = new UserAchievement(UUID.randomUUID(), userMapper.toEntity(user), achievementMapper.toEntity(achievement));
        userAchievementRepositoryPort.save(ua);

        // Aplicar recompensa
        applyReward(user, achievement);

        return true;
    }

    private void applyReward(User user, Achievement achievement) {
        switch (achievement.getRewardType()) {
            case COINS -> user.setCoins(user.getCoins() + achievement.getRewardAmount());
            case ITEM -> {
                // Aquí podrías añadir lógica de inventario si lo implementas
                System.out.println("Item desbloqueado: " + achievement.getRewardItemId());
            }
            case XP -> user.setExperience(user.getExperience() + achievement.getRewardAmount());
        }
        userRepository.save(user);
    }

    @Transactional
    public void checkAchievements(UUID userId, UUID taskId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Achievement> allAchievements = achievementRepository.findAll();

        for (Achievement achievement : allAchievements) {
            boolean alreadyUnlocked = userAchievementRepository
                    .findByUserIdAndAchievementId(userId, achievement.getId())
                    .isPresent();

            if (alreadyUnlocked) continue;

            if (meetsCondition(user, achievement)) {
                unlockAchievement(userId, achievement.getId());
            }
        }
    }

    private boolean meetsCondition(User user, Achievement achievement) {
        return switch (achievement.getConditionType()) {
            case COMPLETE_TASKS -> {
                // Consultar cuántas tareas completó el usuario
                int completedTasks = taskRepository.countCompletedTasksByUserId(user.getId());
                yield completedTasks >= achievement.getConditionValue();
            }
            case REACH_LEVEL -> user.getLevel() >= achievement.getConditionValue();
            case DAILY_STREAK -> user.getStreakCount() >= achievement.getConditionValue();
            case SPEND_COINS -> {
                // Necesitamos implementar tracking de monedas gastadas
                // de momento devolvemos false
                yield false;
            }
            case CUSTOM -> false; // para lógica especial/manual
        };
    }
}
