package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataTaskRepository extends JpaRepository<JpaTaskEntity, UUID>{
    List<JpaTaskEntity> findByUser(JpaUserEntity user);

    @Query("SELECT COUNT(t) FROM JpaTaskEntity t WHERE t.user.id = :userId AND t.done = true")
    int countCompletedTasksByUserId(UUID userId);
}
