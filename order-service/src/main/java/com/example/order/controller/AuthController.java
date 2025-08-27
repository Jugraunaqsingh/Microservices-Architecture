package com.example.order.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final InMemoryUserDetailsManager users;
    private final PasswordEncoder encoder;

    public AuthController(InMemoryUserDetailsManager users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    // Register normal USER
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");
        if (users.userExists(username)) {
            return Map.of("status", "ERROR", "message", "User already exists");
        }
        users.createUser(User.withUsername(username)
                .password(encoder.encode(password))
                .roles("USER")
                .build());
        return Map.of("status", "OK", "message", "Registered");
    }

    // Who am I (validates credentials when called with Basic auth)
    @GetMapping("/whoami")
    public Map<String, Object> whoami(Authentication auth) {
        return Map.of(
                "username", auth.getName(),
                "authorities", auth.getAuthorities());
    }
}
