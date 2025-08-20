package com.example.NoteFlow_Backend.Repo;

import com.example.NoteFlow_Backend.Entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepo extends JpaRepository<Folder, Long> {
    List<Folder> findByUser_UserIdOrderByTitleAsc(Long userId);

    Optional<Folder> findByUser_UserIdAndTitleIgnoreCase(Long userId, String title);
}
