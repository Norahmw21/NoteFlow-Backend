// LoginRequest.java
package com.example.NoteFlow_Backend.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank String usernameOrEmail,
        @NotBlank String password
) {}
