package com.example.NoteFlow_Backend.Service;


import com.example.NoteFlow_Backend.Entity.Folder;
import com.example.NoteFlow_Backend.Repo.FolderRepo;
import com.example.NoteFlow_Backend.Repo.UserRepo;
import com.example.NoteFlow_Backend.dto.FolderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepo folderRepo;
    private final UserRepo userRepo;

    public List<FolderDto> listMine(Long uid) {
        return folderRepo.findByUser_UserIdOrderByTitleAsc(uid)
                .stream().map(f -> new FolderDto(f.getFolderId(), f.getTitle()))
                .toList();
    }

    public FolderDto create(Long uid, String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title is required");

        folderRepo.findByUser_UserIdAndTitleIgnoreCase(uid, title.trim()).ifPresent(x -> {
            throw new DataIntegrityViolationException("Subject with same title already exists");
        });

        var user = userRepo.findById(uid).orElseThrow();
        Folder f = new Folder();
        f.setUser(user);
        f.setTitle(title.trim());
        return toDto(folderRepo.save(f));
    }

    public FolderDto rename(Long uid, Long id, String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title is required");
        Folder f = requireOwned(uid, id);
        f.setTitle(title.trim());
        return toDto(folderRepo.save(f));
    }

    public void delete(Long uid, Long id) {
        folderRepo.delete(requireOwned(uid, id));
    }

    private Folder requireOwned(Long uid, Long id) {
        Folder f = folderRepo.findById(id).orElseThrow();
        if (!f.getUser().getUserId().equals(uid)) throw new RuntimeException("Forbidden");
        return f;
    }

    private FolderDto toDto(Folder f) {
        return new FolderDto(f.getFolderId(), f.getTitle());
    }
}

