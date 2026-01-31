package com.springboot.todoApplication.repository;

import com.springboot.todoApplication.dto.AuthRequest;
import com.springboot.todoApplication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<AuthRequest> findByEmail(String email);
    boolean existsByEmail(String email);
}
