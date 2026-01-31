package com.springboot.todoApplication.service;

import com.springboot.todoApplication.model.Todo;
import com.springboot.todoApplication.dto.TodoDTO;
import com.springboot.todoApplication.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TodoDTO> getByUserId(String id) {
        return todoRepository.findByUserId(id);
    }

    @Override
    public TodoDTO saveTodo(TodoDTO todoDTO) {
        Todo todo = convertToEntity(todoDTO);
        todo.setCreated(LocalDateTime.now());
        Todo saveTodo = todoRepository.save(todo);
        return convertToDTO(saveTodo);
    }

    @Override
    public TodoDTO updateTodo(String id, TodoDTO todoDTO) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setUserId(todoDTO.userId());
        todo.setTaskName(todoDTO.taskName());
        todo.setCategory(todoDTO.category());
        todo.setDone(todoDTO.isDone());
        todo.setFromDate(todoDTO.fromDate());
        todo.setToDate(todoDTO.toDate());
        todo.setCreated(todo.getCreated());
        Todo updatedTodo = todoRepository.save(todo);
        return convertToDTO(updatedTodo);
    }

    @Override
    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateTodoStatus(String id, String userId, boolean isDone) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId).
                orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.setDone(isDone);
        todoRepository.save(todo);
    }

    // Convert Todo Entity to TodoDTO
    private TodoDTO convertToDTO(Todo todo) {
        return new TodoDTO(todo.getId(), todo.getUserId(), todo.getTaskName(), todo.getFromDate(), todo.getToDate(), todo.getCategory(), todo.getDone(), todo.getCreated());
    }

    // Convert TodoDTO to Todo Entity
    private Todo convertToEntity(TodoDTO todoDTO) {
        Todo todo = new Todo();
        todo.setUserId(todoDTO.userId());
        todo.setCategory(todoDTO.category());
        todo.setCreated(todoDTO.created());
        todo.setDone(todoDTO.isDone());
        todo.setFromDate(todoDTO.fromDate());
        todo.setToDate(todoDTO.toDate());
        todo.setTaskName(todoDTO.taskName());
        return todo;
    }
}
