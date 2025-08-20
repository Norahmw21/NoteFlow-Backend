package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Service.FolderService;
import com.example.NoteFlow_Backend.dto.FolderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping
    public List<FolderDto> listMine(@RequestParam Long userId) {
        return folderService.listMine(userId);
    }

    @PostMapping
    public FolderDto create(@RequestParam Long userId, @RequestParam String title) {
        return folderService.create(userId, title);
    }

    @PutMapping("/{id}")
    public FolderDto rename(@RequestParam Long userId,
                            @PathVariable Long id,
                            @RequestParam String title) {
        return folderService.rename(userId, id, title);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestParam Long userId, @PathVariable Long id) {
        folderService.delete(userId, id);
    }
}

