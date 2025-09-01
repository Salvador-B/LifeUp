package com.example.lifeup.adapters.outbound.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class JpaUserEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
    private int level;
    private int experience;
    private int health;
    private int maxHealth;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pass;
    private int coins;
    private int streakCount;
    private LocalDateTime createdAt;

    public JpaUserEntity() {}

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JpaDailyTaskEntity> dailyTasks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JpaTaskEntity> tasks = new ArrayList<>();
}
