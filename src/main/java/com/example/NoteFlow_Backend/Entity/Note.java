package com.example.NoteFlow_Backend.Entity;

import com.example.NoteFlow_Backend.Entity.User;
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
