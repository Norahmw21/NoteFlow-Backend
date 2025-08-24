package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Entity.ToDoList;
import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Service.ToDoListService;
import com.example.NoteFlow_Backend.dto.ToDoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoListController {

    private final ToDoListService toDoListService;

    // C -> create task
    @PostMapping
    public ResponseEntity<ToDoListDto> createTask(@RequestBody ToDoList task) {
        if (task.getUser() == null) {
            task.setUser(new User());
        }
        if (task.getUser().getUserId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        ToDoListDto dto = toDoListService.createTask(task);
        return ResponseEntity.ok(dto);
    }


    // R -> read all tasks for specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ToDoListDto>> getTasksByUserId(@PathVariable Long userId) {
        List<ToDoListDto> tasks = toDoListService.getAllTasksByUser(userId);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    // R -> get task by ID for a specific user
    @GetMapping("/{id}")
    public ResponseEntity<ToDoListDto> getTaskById(@RequestParam Long userId, @PathVariable Long id) {
        return toDoListService.getTaskById(userId, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // U -> update task
    @PutMapping("/{id}")
    public ResponseEntity<ToDoListDto> updateTask(@PathVariable Long id, @RequestBody ToDoList task) {
        if (task.getUser() == null || task.getUser().getUserId() == null) {
            return ResponseEntity.badRequest().body(null); // fail fast if userId missing
        }

        try {
            ToDoListDto updated = toDoListService.updateTask(task.getUser().getUserId(), id, task);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // D -> delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @RequestParam Long userId) {
        try {
            toDoListService.deleteTask(userId, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
