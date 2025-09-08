package com.example.lifeup.application.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.lifeup.domain.model.TaskCompletedEvent;

@Service
public class AchievementListener {
    private final AchievementService achievementService;

    public AchievementListener(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @EventListener
    public void onTaskCompleted(TaskCompletedEvent event) {
        achievementService.checkAchievements(event.getUserId(), event.getTaskId());
    }
}
