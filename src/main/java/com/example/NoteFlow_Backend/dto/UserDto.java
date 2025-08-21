// UserDto.java
package com.example.NoteFlow_Backend.dto;

import jakarta.validation.constraints.*;

public class UserDto {
    private Long id;

    @NotBlank @Size(min=3, max=120)
    private String username;

    @Email @NotBlank @Size(max=160)
    private String email;

    public UserDto() {}

    public UserDto(Long id, String username, String email) {
        this.id = id; this.username = username; this.email = email;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
