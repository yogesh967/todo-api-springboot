package com.springboot.todoApplication.service;

import com.springboot.todoApplication.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    List<TodoDTO> getAllTodos();
    List<TodoDTO> getByUserId(String id);
    TodoDTO saveTodo(TodoDTO todoDTO);
    TodoDTO updateTodo(String id, TodoDTO todoDTO);
    void deleteTodo(String id);
    void updateTodoStatus(String id, String userId, boolean isDone);
}
