package com.example.lifeup.adapters.mapper;

import org.springframework.stereotype.Component;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.domain.model.User;

@Component
public class UserMapper {
    public JpaUserEntity toEntity(User user) {
        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setLevel(user.getLevel());
        entity.setExperience(user.getExperience());
        entity.setHealth(user.getHealth());
        entity.setMaxHealth(user.getMaxHealth());
        entity.setEmail(user.getEmail());
        entity.setPass(user.getPass());
        entity.setCoins(user.getCoins());
        entity.setStreakCount(user.getStreakCount());
        entity.setCreatedAt(user.getCreatedAt());
        return entity;
    }

    public User toDomain(JpaUserEntity entity) {
        return new User(
            entity.getId(),
            entity.getName(),
            entity.getLevel(),
            entity.getExperience(),
            entity.getHealth(),
            entity.getMaxHealth(),
            entity.getEmail(),
            entity.getPass(),
            entity.getCoins(),
            entity.getStreakCount()
        );
    }
}
