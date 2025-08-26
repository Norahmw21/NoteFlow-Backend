package com.example.NoteFlow_Backend.Entity;


//------------------------- Import -------------------------//
import jakarta.persistence.*; //Brings in JPA annotations and types, to map this class to a database table.
import lombok.*; //generates boilerplate (getters/setters/constructors/builder) at compile time.
import java.time.Instant; //Store creation/update/delete times precisely and timezone-agnostic.
import com.fasterxml.jackson.annotation.JsonIgnore; //Jackson annotation to skip a field during JSON serialization when returning Note as JSON

//------------------------- Annotation --------------------//
@Entity //Marks the class as a JPA entity, So Hibernate knows to persist it.
@Table(name = "note") //Sets the DB table name.
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder //For flexible object creation

//------------------------- Class --------------------------//
public class Note {
    @Id //Primary key marker.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-increment
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //Many notes belong to one user
    @JoinColumn(name = "user_id", nullable = false) //FK
    @JsonIgnore // ← prevent user→notes→user recursion
    private User user;

    @Column(nullable = false) //Every note should have a title.
    private String title;

    @Column(name = "text_html", columnDefinition = "text")
    private String textHtml; //Connects with Editor in your frontend; render as HTML.

    @Column(name = "drawing_json", columnDefinition = "text")
    private String drawingJson; //Connects with Client tools that parse/render this JSON.

    @Column(name = "favorite", nullable = false)
    private boolean favorite = false; //New notes aren’t favorites unless set.

    @Column(name = "trashed", nullable = false)
    private boolean trashed = false; //Default not trashed.

    @Column(name = "deleted_at") //For track when a note was marked deleted; allows permanent purge after X days
    private Instant deletedAt; //Holds that timestamp

    @Column(name = "created_at", nullable = false) //Field for created time.
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false) //Last update timestamp column
    private Instant updatedAt;

    @Column(name = "tag_name") //Categorize notes by a simple name
    private String tagName; //Connects with filtering.

    @Column(name = "tag_color", length = 7) //Store hex color
    private String tagColor;

    //------------------------ timestamps ------------------------//
    @PrePersist //JPA lifecycle hook before first insert.
    void onCreate(){ //Automatically set timestamps when the entity is created.
        var now = Instant.now();//Capture current UTC time once.
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate //Keep updatedAt fresh on any change.
    void onUpdate(){
        updatedAt = Instant.now();
    }
}
