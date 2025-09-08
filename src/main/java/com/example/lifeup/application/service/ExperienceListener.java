package com.example.lifeup.application.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.lifeup.domain.model.TaskCompletedEvent;

@Service
public class ExperienceListener {
    private final UserService userService;

    public ExperienceListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void onTaskCompleted(TaskCompletedEvent event) {
        userService.addExperience(event.getUserId(), event.getPoints());
    }
}
