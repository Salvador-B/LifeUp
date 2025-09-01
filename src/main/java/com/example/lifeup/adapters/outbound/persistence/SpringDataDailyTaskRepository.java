package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDailyTaskRepository extends JpaRepository<JpaDailyTaskEntity, UUID> {
    List<JpaDailyTaskEntity> findByUser(JpaUserEntity user);
}
