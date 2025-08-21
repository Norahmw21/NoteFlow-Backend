package com.example.NoteFlow_Backend.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="to_do_list")
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String taskName;//name user will name the task
    private String status;//active or closed
    private Date startDate;
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
