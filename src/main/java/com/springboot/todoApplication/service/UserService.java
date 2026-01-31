package com.springboot.todoApplication.service;

import com.springboot.todoApplication.dto.AuthRequest;
import com.springboot.todoApplication.dto.RegisterRequest;

import java.util.Optional;

public interface UserService {
    RegisterRequest register(RegisterRequest registerRequest);
    Optional<AuthRequest> findByEmail(String email);
    Boolean checkPassword(String raw, String hashed);
}
