package com.springboot.todoApplication.service;

import com.springboot.todoApplication.dto.AuthRequest;
import com.springboot.todoApplication.dto.RegisterRequest;
import com.springboot.todoApplication.model.User;
import com.springboot.todoApplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public RegisterRequest register(RegisterRequest req) {
        if(userRepository.existsByEmail(req.email())) throw new RuntimeException("Email already exists");
        String encodedPassword = encoder.encode(req.password());
        User user = convertToEntity(req);
        user.setPassword(encodedPassword);
        User saveUser = userRepository.save(user);
        return convertToDTO(saveUser);
    }

    @Override
    public Optional<AuthRequest> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean checkPassword(String raw, String hashed) {
        return encoder.matches(raw, hashed);
    }

    // Convert Todo Entity to TodoDTO
    private RegisterRequest convertToDTO(User user) {
        return new RegisterRequest(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    // Convert TodoDTO to Todo Entity
    private User convertToEntity(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setEmail(registerRequest.email());
        user.setPassword(registerRequest.password());
        return user;
    }
}
