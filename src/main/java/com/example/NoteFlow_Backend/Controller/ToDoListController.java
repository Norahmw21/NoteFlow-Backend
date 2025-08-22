package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Entity.ToDoList;
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

    // Helper: Get current user ID from JWT / Security context
    private Long getCurrentUserId() {
        // Replace this with your actual JWT/Security implementation
        // Example:
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // return ((CustomUserDetails) auth.getPrincipal()).getUserId();
        return 1L; // placeholder for testing
    }

    // C -> create task
    @PostMapping
    public ResponseEntity<ToDoListDto> createTask(@RequestBody ToDoList task) {
        task.setUser(new com.example.NoteFlow_Backend.Entity.User());
        task.getUser().setUserId(getCurrentUserId()); // assign current user
        ToDoListDto dto = toDoListService.createTask(task);
        return ResponseEntity.ok(dto);
    }

    // R -> read all tasks for current user
    @GetMapping
    public ResponseEntity<List<ToDoListDto>> getAllTasks() {
        List<ToDoListDto> tasks = toDoListService.getAllTasksByUser(getCurrentUserId());
        return ResponseEntity.ok(tasks);
    }

    // R -> get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<ToDoListDto> getTaskById(@PathVariable Long id) {
        return toDoListService.getTaskById(getCurrentUserId(), id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // U -> update task
    @PutMapping("/{id}")
    public ResponseEntity<ToDoListDto> updateTask(@PathVariable Long id, @RequestBody ToDoList updatedTask) {
        updatedTask.setUser(new com.example.NoteFlow_Backend.Entity.User());
        updatedTask.getUser().setUserId(getCurrentUserId()); // assign current user
        try {
            ToDoListDto dto = toDoListService.updateTask(getCurrentUserId(), id, updatedTask);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // D -> delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            toDoListService.deleteTask(getCurrentUserId(), id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
