package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Entity.Note;
import com.example.NoteFlow_Backend.Service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    // Active notes of a user
    @GetMapping
    public ResponseEntity<List<Note>> list(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listByUser(userId));
    }

    // Trash & favorites
    @GetMapping("/trash")
    public ResponseEntity<List<Note>> trash(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listTrashByUser(userId));
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<Note>> favorites(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listFavoritesByUser(userId));
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Note> get(@PathVariable Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getByUser(userId, id));
    }

    @PostMapping
    public ResponseEntity<Note> create(@PathVariable Long userId, @RequestBody Note note) {
        return ResponseEntity.ok(service.create(userId, note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long userId, @PathVariable Long id, @RequestBody Note note) {
        return ResponseEntity.ok(service.update(userId, id, note));
    }

    // Soft-delete (trash / restore)
    @PutMapping("/{id}/trash")
    public ResponseEntity<Note> setTrashed(@PathVariable Long userId, @PathVariable Long id, @RequestParam boolean value) {
        return ResponseEntity.ok(service.setTrashed(userId, id, value));
    }

    // Favorite toggle
    @PutMapping("/{id}/favorite")
    public ResponseEntity<Note> setFavorite(@PathVariable Long userId, @PathVariable Long id, @RequestParam boolean value) {
        return ResponseEntity.ok(service.setFavorite(userId, id, value));
    }

    // Permanent delete (from Trash)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermanent(@PathVariable Long userId, @PathVariable Long id) {
        service.deletePermanent(userId, id);
        return ResponseEntity.noContent().build();
    }
}
