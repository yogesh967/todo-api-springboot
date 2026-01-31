package com.springboot.todoApplication.dto;

public record RegisterRequest(String id, String firstName, String lastName, String email, String password) {
}
