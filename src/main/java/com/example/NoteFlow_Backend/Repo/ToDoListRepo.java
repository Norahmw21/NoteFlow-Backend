package com.example.NoteFlow_Backend.Repo;

import com.example.NoteFlow_Backend.Entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToDoListRepo extends JpaRepository<ToDoList, Long> {

    // Access userId inside User entity
    List<ToDoList> findByUser_UserId(Long userId);

    // Example for filtering by status and user
    List<ToDoList> findByStatusAndUser_UserId(String status, Long userId);
}

