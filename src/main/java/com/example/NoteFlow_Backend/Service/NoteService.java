package com.example.NoteFlow_Backend.Service;

//---------------------- Import ---------------------------//
import com.example.NoteFlow_Backend.Entity.Note;
import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Repo.NoteRepository;
import com.example.NoteFlow_Backend.Repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

//--------------------- Service Class -------------------//
@Service //A class annotated with @Service. Contains business logic (rules, validation, calculations, transactions).
public class NoteService {
    private final NoteRepository repo;
    private final UserRepo userRepo;

    //---------- Constructor injection ------------//
    //Immutability, testability, avoids field injection pitfalls.
    public NoteService(NoteRepository repo, UserRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    //--------------- Fetch user ------------------//
    //ensure user exists; avoids nulls.
    private User getUser(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")); //Connects with: UserRepo.findById; exceptions bubble to controller (prefer mapping to 404).
    }

    //------------------ Lists ------------------//
    // View Notes
    public List<Note> listByUser(Long userId) {
        return repo.findAllByUserUserIdAndTrashedFalseOrderByUpdatedAtDesc(userId); //Get active (not trashed) notes for a user, newest first.
    }

    //Filter by tag name/color
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

    //Show only trashed notes.
    public List<Note> listTrashByUser(Long userId) {
        return repo.findAllByUserUserIdAndTrashedTrueOrderByUpdatedAtDesc(userId);
    }

    //Show only favorite notes
    public List<Note> listFavoritesByUser(Long userId) {
        return repo.findAllByUserUserIdAndFavoriteTrueAndTrashedFalseOrderByUpdatedAtDesc(userId);
    }

    //------------------ Single notes -----------------//
    //Fetch a note that belongs to the given user.
    public Note getByUser(Long userId, Long noteId) {
        return repo.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for user"));
    }

    // ---------------- Transactional ----------------//
    @Transactional //transaction is a sequence of database operations that are treated as a single unit of work
    public Note create(Long userId, Note n) {
        User user = getUser(userId);
        if (n.getTitle() == null || n.getTitle().isBlank()) n.setTitle("Untitled");
        n.setId(null);
        n.setUser(user);
        n.setTrashed(false);
        n.setFavorite(false);
        n.setDeletedAt(null);
        return repo.save(n);
    }

    @Transactional
    public Note update(Long userId, Long noteId, Note incoming) {
        var n = getByUser(userId, noteId);
        if (incoming.getTitle() != null) n.setTitle(incoming.getTitle());
        if (incoming.getTextHtml() != null) n.setTextHtml(incoming.getTextHtml());
        if (incoming.getDrawingJson() != null) n.setDrawingJson(incoming.getDrawingJson());
        if (incoming.getTagName() != null) n.setTagName(incoming.getTagName());
        if (incoming.getTagColor() != null) n.setTagColor(incoming.getTagColor());
        return n;
    }

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

    @Transactional
    public void deletePermanent(Long userId, Long noteId) {
        var n = getByUser(userId, noteId);
        repo.delete(n);
    }
}
