package com.example.lifeup.adapters.outbound.persistence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.lifeup.domain.model.RecurrenceType;
import com.example.lifeup.domain.model.TaskDifficulty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "daily_tasks")
public class JpaDailyTaskEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(nullable = false)
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

    public JpaDailyTaskEntity() {}
}
