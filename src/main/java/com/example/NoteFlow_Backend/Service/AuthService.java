package com.example.NoteFlow_Backend.Service;

import com.example.NoteFlow_Backend.dto.RegisterRequest;
import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(RegisterRequest req) {
        if (repo.existsByUsername(req.username()))
            throw new IllegalArgumentException("Username already taken");
        if (repo.existsByEmail(req.email()))
            throw new IllegalArgumentException("Email already in use");

        User u = new User();
        u.setUsername(req.username().trim());
        u.setEmail(req.email().trim().toLowerCase());
        u.setPasswordH(encoder.encode(req.password()));
        return repo.save(u);
    }

    public User validateCredentials(String usernameOrEmail, String rawPassword) {
        return repo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .filter(u -> encoder.matches(rawPassword, u.getPasswordH()))
                .orElse(null);
    }
}
