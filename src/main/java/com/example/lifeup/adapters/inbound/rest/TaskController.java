package com.example.lifeup.adapters.inbound.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lifeup.adapters.dto.TaskDto;
import com.example.lifeup.adapters.mapper.TaskMapper;
import com.example.lifeup.application.service.TaskService;
import com.example.lifeup.domain.model.Task;
import com.example.lifeup.domain.model.TaskDifficulty;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service){
        this.service = service;
    }

    record CreateTaskRequest(String title, String description, LocalDate dueDate, TaskDifficulty difficulty) {}

    @PostMapping
    public TaskDto create(@RequestBody CreateTaskRequest req){
        Task created = service.createTask(req.title(), req.description(), req.dueDate(), req.difficulty());
        return TaskMapper.toDto(created);
    }

    @GetMapping
    public List<TaskDto> list() {
        return service.findAll()
                .stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID id, @RequestBody Task task) {
        try {
            Task updated = service.updateTask(id, task);
            return ResponseEntity.ok(TaskMapper.toDto(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable UUID id) {
        System.out.println("➡️ [Controller] completeTask llamado con id=" + id);
        try {
            Task completed = service.completeTask(id);
            System.out.println("✅ [Controller] tarea completada: " + completed.getId());
            return ResponseEntity.ok(TaskMapper.toDto(completed));
        } catch (RuntimeException e) {
            System.out.println("❌ [Controller] error completando tarea: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
