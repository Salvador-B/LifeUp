package com.example.lifeup.adapters.inbound.rest;

import org.springframework.web.bind.annotation.*;

import com.example.lifeup.application.service.UserService;
import com.example.lifeup.domain.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public User getUser() {
        return service.getUser();
    }
}
