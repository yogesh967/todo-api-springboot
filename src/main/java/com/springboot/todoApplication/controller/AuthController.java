package com.springboot.todoApplication.controller;

import com.springboot.todoApplication.dto.AuthRequest;
import com.springboot.todoApplication.dto.AuthResponse;
import com.springboot.todoApplication.dto.RegisterRequest;
import com.springboot.todoApplication.security.JwtUtil;
import com.springboot.todoApplication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        RegisterRequest created = userService.register(registerRequest);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<AuthRequest> user = userService.findByEmail(authRequest.email());
        if(user.isEmpty() || !userService.checkPassword(authRequest.password(), user.get().password())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.get().id());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName(); // JWT subject (set in JwtFilter)
        return ResponseEntity.ok(userService.findByEmail(email));
    }
}
