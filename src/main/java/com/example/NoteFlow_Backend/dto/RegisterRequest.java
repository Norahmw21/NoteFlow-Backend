// RegisterRequest.java
package com.example.NoteFlow_Backend.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank @Size(min=3, max=120) String username,
        @Email @NotBlank @Size(max=160) String email,
        @NotBlank @Size(min=6, max=100) String password
) {}
