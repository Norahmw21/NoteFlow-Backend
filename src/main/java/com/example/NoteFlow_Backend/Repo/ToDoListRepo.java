package com.example.NoteFlow_Backend.Repo;

import com.example.NoteFlow_Backend.Entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepo extends JpaRepository<ToDoList,Long> {

}
