//Handles all database CRUD operations automatically (insert, update, delete, find).
//No need to write SQL manually; JPA does it for you.
package com.example.NoteFlow_Backend.Repo;

//------------------- Import --------------------//
import com.example.NoteFlow_Backend.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

//-------------------- Repo --------------------//
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserUserIdAndTrashedFalseOrderByUpdatedAtDesc(Long userId);
    List<Note> findAllByUserUserIdAndTrashedTrueOrderByUpdatedAtDesc(Long userId);
    List<Note> findAllByUserUserIdAndFavoriteTrueAndTrashedFalseOrderByUpdatedAtDesc(Long userId);
    List<Note> findAllByUserUserIdAndTrashedFalseAndTagNameIgnoreCaseOrderByUpdatedAtDesc(Long userId, String tagName);
    List<Note> findAllByUserUserIdAndTrashedFalseAndTagColorOrderByUpdatedAtDesc(Long userId, String tagColor);
    List<Note> findAllByUserUserIdAndTrashedFalseAndTagNameIgnoreCaseAndTagColorOrderByUpdatedAtDesc(Long userId, String tagName, String tagColor);
    Optional<Note> findByIdAndUserUserId(Long id, Long userId);
}


