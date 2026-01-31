package com.springboot.todoApplication.dto;

import java.time.LocalDateTime;

public record TodoDTO(String id, String userId, String taskName, LocalDateTime fromDate, LocalDateTime toDate, String category, Boolean isDone, LocalDateTime created) {
}
