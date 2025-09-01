package com.example.lifeup.adapters.outbound.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTaskRepository extends JpaRepository<JpaTaskEntity, UUID>{
    List<JpaTaskEntity> findByUser(JpaUserEntity user);
}
