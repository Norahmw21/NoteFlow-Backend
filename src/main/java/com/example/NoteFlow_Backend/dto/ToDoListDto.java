package com.example.NoteFlow_Backend.dto;

import java.util.Date;

public record ToDoListDto (
    Long taskId,
    String taskName,//name user will name the task
    String status,//active or closed
    Date startDate,
    Date endDate,
    String taskImportance,//high normal low
    Long userID
){}
