package com.springboot.todoApplication.controller;

import com.springboot.todoApplication.dto.TodoDTO;
import com.springboot.todoApplication.security.CustomUserPrincipal;
import com.springboot.todoApplication.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {
    private final TodoService todoService;


    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/fetchAllTodos")
    public ResponseEntity<List<TodoDTO>> getAllTodos(Authentication authentication) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        String userId = principal.getUserId();

        return ResponseEntity.ok(todoService.getByUserId(userId));
    }

//    @GetMapping("/getTodo/{id}")
//    public ResponseEntity<List<TodoDTO>> getTodoById(@PathVariable String id) {
//        return ResponseEntity.ok(todoService.getTodoByUserId(id));
//    }

    @PostMapping("/createTodo")
    public ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO todoDTO) {
        return ResponseEntity.status(201).body(todoService.saveTodo(todoDTO));
    }

    @PutMapping("/updateTodo/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable String id, @RequestBody TodoDTO todoDTO) {
        try {
            TodoDTO updatedTodo = todoService.updateTodo(id, todoDTO);
            return ResponseEntity.ok(updatedTodo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Todo deleted successfully");
        response.put("status", "success");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/doneTodo/{id}")
    public ResponseEntity<Map<String, String>> updateTodoStatus(
            @PathVariable String id,
            @RequestBody TodoDTO todoDTO,
            Authentication authentication
    ) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        String userId = principal.getUserId();

        todoService.updateTodoStatus(id, userId, todoDTO.isDone());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Todo status updated successfully");
        response.put("status", "success");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
