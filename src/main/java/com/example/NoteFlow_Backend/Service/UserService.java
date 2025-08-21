package com.example.NoteFlow_Backend.Service;

import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(Long id, User updated) {
        User existing = getUser(id);
        existing.setUsername(updated.getUsername());
        existing.setEmail(updated.getEmail());
        existing.setAvatarUrl(updated.getAvatarUrl());
        existing.setPhone(updated.getPhone());
        return userRepo.save(existing);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
