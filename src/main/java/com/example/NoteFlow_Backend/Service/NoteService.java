package com.example.NoteFlow_Backend.Service;

import com.example.NoteFlow_Backend.Entity.Note;
import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Repo.NoteRepository;
import com.example.NoteFlow_Backend.Repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository repo;
    private final UserRepo userRepo;

    public NoteService(NoteRepository repo, UserRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // 🔹 Helper: fetch user
    private User getUser(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================= Lists =================
    public List<Note> listByUser(Long userId) {
        return repo.findAllByUserUserIdAndTrashedFalseOrderByUpdatedAtDesc(userId);
    }

    // NEW: overload with tag filters
    public List<Note> listByUser(Long userId, String tagName, String tagColor) {
        boolean hasName = tagName != null && !tagName.isBlank();
        boolean hasColor = tagColor != null && !tagColor.isBlank();

        if (hasName && hasColor) {
            return repo.findAllByUserUserIdAndTrashedFalseAndTagNameIgnoreCaseAndTagColorOrderByUpdatedAtDesc(
                    userId, tagName.trim(), tagColor.trim());
        }
        if (hasName) {
            return repo.findAllByUserUserIdAndTrashedFalseAndTagNameIgnoreCaseOrderByUpdatedAtDesc(
                    userId, tagName.trim());
        }
        if (hasColor) {
            return repo.findAllByUserUserIdAndTrashedFalseAndTagColorOrderByUpdatedAtDesc(userId, tagColor.trim());
        }
        return listByUser(userId);
    }

    public List<Note> listTrashByUser(Long userId) {
        return repo.findAllByUserUserIdAndTrashedTrueOrderByUpdatedAtDesc(userId);
    }

    public List<Note> listFavoritesByUser(Long userId) {
        return repo.findAllByUserUserIdAndFavoriteTrueAndTrashedFalseOrderByUpdatedAtDesc(userId);
    }

    // ================= Single Note =================
    public Note getByUser(Long userId, Long noteId) {
        return repo.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for user"));
    }

    // ================= Create =================
    @Transactional
    public Note create(Long userId, Note n) {
        User user = getUser(userId);
        if (n.getTitle() == null || n.getTitle().isBlank()) n.setTitle("Untitled");
        n.setId(null);
        n.setUser(user);  // 🔹 attach user
        n.setTrashed(false);
        n.setFavorite(false);
        n.setDeletedAt(null);
        // tagName/tagColor accepted as provided (nullable)
        return repo.save(n);
    }

    // ================= Update =================
    @Transactional
    public Note update(Long userId, Long noteId, Note incoming) {
        var n = getByUser(userId, noteId);
        if (incoming.getTitle() != null) n.setTitle(incoming.getTitle());
        if (incoming.getTextHtml() != null) n.setTextHtml(incoming.getTextHtml());
        if (incoming.getDrawingJson() != null) n.setDrawingJson(incoming.getDrawingJson());
        if (incoming.getTagName() != null) n.setTagName(incoming.getTagName());
        if (incoming.getTagColor() != null) n.setTagColor(incoming.getTagColor());
        return n; // dirty-checked
    }

    // ================= Favorite & Trash =================
    @Transactional
    public Note setFavorite(Long userId, Long noteId, boolean value) {
        var n = getByUser(userId, noteId);
        n.setFavorite(value);
        return n;
    }

    @Transactional
    public Note setTrashed(Long userId, Long noteId, boolean value) {
        var n = getByUser(userId, noteId);
        n.setTrashed(value);
        n.setDeletedAt(value ? Instant.now() : null);
        return n;
    }

    // ================= Permanent Delete =================
    @Transactional
    public void deletePermanent(Long userId, Long noteId) {
        var n = getByUser(userId, noteId);
        repo.delete(n);
    }
}
