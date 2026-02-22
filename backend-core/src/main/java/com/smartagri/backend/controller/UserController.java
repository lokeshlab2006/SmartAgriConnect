package com.smartagri.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String phone = credentials.get("phone");
        // Mock authentication returning a dummy token and user info
        return Map.of(
            "token", "mock-jwt-token-for-" + phone,
            "user", Map.of(
                "id", 1,
                "name", "Farmer " + phone,
                "role", "FARMER",
                "phone", phone
            )
        );
    }
}
