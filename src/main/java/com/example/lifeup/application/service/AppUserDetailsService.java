package com.example.lifeup.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final SpringDataUserRepository repo;
    public AppUserDetailsService(SpringDataUserRepository repo){ this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        JpaUserEntity u = repo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("No user " + email));
        return org.springframework.security.core.userdetails.User
            .withUsername(u.getEmail())
            .password(u.getPass())
            .authorities("ROLE_USER")
            .build();
    }
}
