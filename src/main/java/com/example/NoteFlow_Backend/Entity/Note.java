package com.example.NoteFlow_Backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "note")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore                  // ← prevent user→notes→user recursion
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(name = "text_html", columnDefinition = "text")
    private String textHtml;

    @Column(name = "drawing_json", columnDefinition = "text")
    private String drawingJson;

    @Column(name = "favorite", nullable = false)
    private boolean favorite = false;

    @Column(name = "trashed", nullable = false)
    private boolean trashed = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // NEW: Tag fields
    @Column(name = "tag_name")
    private String tagName;             // e.g., "Algorithms"

    @Column(name = "tag_color", length = 7)
    private String tagColor;            // hex like "#3B82F6"

    @PrePersist
    void onCreate(){
        var now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate(){
        updatedAt = Instant.now();
    }
}
