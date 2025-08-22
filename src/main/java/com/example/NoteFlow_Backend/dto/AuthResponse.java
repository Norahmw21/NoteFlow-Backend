// AuthResponse.java
package com.example.NoteFlow_Backend.dto;

public record AuthResponse(
        boolean ok,
        String token,
        String username,
        String email
) {}
