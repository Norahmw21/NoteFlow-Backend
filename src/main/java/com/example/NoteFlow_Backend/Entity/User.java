package com.example.NoteFlow_Backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 120)
    private String username;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(name = "password_h", nullable = false, length = 255)
    private String passwordH;

    @Column(length = 255)
    private String avatarUrl;   // link to profile picture

    @Column(length = 20)
    private String phone;       // optional phone number

    // 🔹 New: One user owns many notes
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;
}