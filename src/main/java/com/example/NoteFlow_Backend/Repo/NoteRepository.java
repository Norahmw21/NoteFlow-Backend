package com.example.NoteFlow_Backend.Repo;

import com.example.NoteFlow_Backend.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserUserIdAndTrashedFalseOrderByUpdatedAtDesc(Long userId);
    List<Note> findAllByUserUserIdAndTrashedTrueOrderByUpdatedAtDesc(Long userId);
    List<Note> findAllByUserUserIdAndFavoriteTrueAndTrashedFalseOrderByUpdatedAtDesc(Long userId);

    Optional<Note> findByIdAndUserUserId(Long id, Long userId);
}

