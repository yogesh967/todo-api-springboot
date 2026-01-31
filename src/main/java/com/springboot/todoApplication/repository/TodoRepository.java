package com.springboot.todoApplication.repository;

import com.springboot.todoApplication.model.Todo;
import com.springboot.todoApplication.dto.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<Todo, String> {
    List<TodoDTO> findByUserId(String userId);
    Optional<Todo> findByIdAndUserId(String id, String userId);
}
