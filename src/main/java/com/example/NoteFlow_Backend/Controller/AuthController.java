package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.dto.*;
import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Security.JwtService;
import com.example.NoteFlow_Backend.Service.AuthService;
import com.example.NoteFlow_Backend.dto.AuthResponse;
import com.example.NoteFlow_Backend.dto.LoginRequest;
import com.example.NoteFlow_Backend.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwt;

    public AuthController(AuthService authService, JwtService jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        User u = authService.register(req);
        String token = jwt.generateToken(u.getUsername(), Map.of("uid", u.getUserId(), "email", u.getEmail()));
        return new AuthResponse(true, token, u.getUsername(), u.getEmail());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        User u = authService.validateCredentials(req.usernameOrEmail(), req.password());
        if (u == null) return new AuthResponse(false, null, null, null);
        String token = jwt.generateToken(u.getUsername(), Map.of("uid", u.getUserId(), "email", u.getEmail()));
        return new AuthResponse(true, token, u.getUsername(), u.getEmail());
    }

    // Example protected endpoint (useful to test)
    @GetMapping("/me")
    public Map<String, Object> me(@RequestHeader("Authorization") String auth) {
        return Map.of("ok", true);
    }
}
