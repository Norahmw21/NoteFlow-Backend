package com.example.NoteFlow_Backend.dto;

public record UserProfileDto(
        Long id,
        String username,
        String email,
        String avatarUrl,
        String phone
) {}
