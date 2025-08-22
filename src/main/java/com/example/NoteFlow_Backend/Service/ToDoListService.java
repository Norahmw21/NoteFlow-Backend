package com.example.NoteFlow_Backend.Service;

import com.example.NoteFlow_Backend.Entity.ToDoList;
import com.example.NoteFlow_Backend.Repo.ToDoListRepo;
import com.example.NoteFlow_Backend.dto.ToDoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoListService {
    private final ToDoListRepo toDoListRepo;

    //C-> create
    public ToDoListDto createTask(ToDoList task){
        ToDoList newTask= toDoListRepo.save(task);
        return new ToDoListDto(
                newTask.getTaskId(),
                newTask.getTaskName(),
                newTask.getStatus(),
                newTask.getStartDate(),
                newTask.getEndDate(),
                newTask.getTaskImportance(),
                newTask.getUser().getUserId()
        );

    }
    //R -> read all tasks for specific user
    public List<ToDoListDto> getAllTasksByUser(Long userId) {
        return toDoListRepo.findByUser_UserId(userId)
                // only tasks for this user
                .stream()
                .map(task -> new ToDoListDto(
                        task.getTaskId(),
                        task.getTaskName(),
                        task.getStatus(),
                        task.getStartDate(),
                        task.getEndDate(),
                        task.getTaskImportance(),
                        task.getUser().getUserId()
                ))
                .collect(Collectors.toList());
    }
    // R -> get task by ID for a specific user
    public Optional<ToDoListDto> getTaskById(Long userId, Long taskId) {
        return toDoListRepo.findById(taskId)
                .filter(task -> task.getUser().getUserId().equals(userId))
                .map(task -> new ToDoListDto(
                        task.getTaskId(),
                        task.getTaskName(),
                        task.getStatus(),
                        task.getStartDate(),
                        task.getEndDate(),
                        task.getTaskImportance(),
                        task.getUser().getUserId()
                ));
    }
    // U -> update task for a specific user
    public ToDoListDto updateTask(Long userId, Long taskId, ToDoList updatedTask) {
        ToDoList savedTask = toDoListRepo.findById(taskId)
                .filter(task -> task.getUser().getUserId().equals(userId))
                .map(task -> {
                    task.setTaskName(updatedTask.getTaskName());
                    task.setStatus(updatedTask.getStatus());
                    task.setStartDate(updatedTask.getStartDate());
                    task.setEndDate(updatedTask.getEndDate());
                    task.setTaskImportance(updatedTask.getTaskImportance());
                    return toDoListRepo.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found or does not belong to user"));

        return new ToDoListDto(
                savedTask.getTaskId(),
                savedTask.getTaskName(),
                savedTask.getStatus(),
                savedTask.getStartDate(),
                savedTask.getEndDate(),
                savedTask.getTaskImportance(),
                savedTask.getUser().getUserId()
        );
    }

    // D -> delete task for a specific user
    public void deleteTask(Long userId, Long taskId) {
        ToDoList task = toDoListRepo.findById(taskId)
                .filter(t -> t.getUser().getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Task not found or does not belong to user"));

        toDoListRepo.delete(task);
    }

}


