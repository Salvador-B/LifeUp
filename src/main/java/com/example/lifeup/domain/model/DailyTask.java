package com.example.lifeup.domain.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;

public class DailyTask {
    private UUID id;
    private JpaUserEntity user;
    private String title;
    private String description;
    private boolean doneToday;
    private LocalDate lastCompletedDate;
    private int points;
    private TaskDifficulty difficulty;
    private LocalDate startDate; 
    private RecurrenceType recurrenceType; 
    private int repeatEvery; 
    private List<DayOfWeek> daysOfWeek; 

    public DailyTask(UUID id, JpaUserEntity user, String title, String description, boolean doneToday, LocalDate lastCompletedDate, int points, TaskDifficulty difficulty,
                     LocalDate startDate, RecurrenceType recurrenceType, int repeatEvery, List<DayOfWeek> daysOfWeek) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.doneToday = doneToday;
        this.lastCompletedDate = lastCompletedDate;
        this.points = points;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.recurrenceType = recurrenceType;
        this.repeatEvery = repeatEvery;
        this.daysOfWeek = daysOfWeek;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public JpaUserEntity getUser() { return user; }
    public void setUser(JpaUserEntity user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getLastCompletedDate() { return lastCompletedDate; }
    public void setLastCompletedDate(LocalDate lastCompletedDate) { this.lastCompletedDate = lastCompletedDate; }

    public boolean isDoneToday() { return doneToday; }
    public void setDoneToday(boolean doneToday) { this.doneToday = doneToday; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public TaskDifficulty getDifficulty() { return difficulty; }
    public void setDifficulty(TaskDifficulty difficulty) { this.difficulty = difficulty; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public RecurrenceType getRecurrenceType() { return recurrenceType; }
    public void setRecurrenceType(RecurrenceType recurrenceType) { this.recurrenceType = recurrenceType; }

    public int getRepeatEvery() { return repeatEvery; }
    public void setRepeatEvery(int repeatEvery) { this.repeatEvery = repeatEvery; }

    public List<DayOfWeek> getDaysOfWeek() { return daysOfWeek; }
    public void setDaysOfWeek(List<DayOfWeek> daysOfWeek) { this.daysOfWeek = daysOfWeek; }
}
