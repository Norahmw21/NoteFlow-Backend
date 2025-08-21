package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> list() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

