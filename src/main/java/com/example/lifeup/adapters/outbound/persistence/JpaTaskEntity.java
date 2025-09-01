package com.example.lifeup.adapters.outbound.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.lifeup.domain.model.TaskDifficulty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tasks")
public class JpaTaskEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private JpaUserEntity user;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean done;
    private int points;
    private LocalDateTime createdAt;
    private TaskDifficulty difficulty;

    public JpaTaskEntity() {}
}
