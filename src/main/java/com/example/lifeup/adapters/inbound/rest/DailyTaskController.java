package com.example.lifeup.adapters.inbound.rest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lifeup.adapters.dto.DailyTaskDto;
import com.example.lifeup.adapters.mapper.DailyTaskMapper;
import com.example.lifeup.application.service.DailyTaskService;
import com.example.lifeup.domain.model.DailyTask;
import com.example.lifeup.domain.model.RecurrenceType;
import com.example.lifeup.domain.model.TaskDifficulty;

@RestController
@RequestMapping("/api/daily-tasks")
public class DailyTaskController {
    private final DailyTaskService service;

    public DailyTaskController(DailyTaskService service) {
        this.service = service;
    }

    record CreateDailyTaskRequest(String title, String description, TaskDifficulty difficulty, 
                                  LocalDate startDate, RecurrenceType recurrenceType, int repeatEvery, List<DayOfWeek> daysOfWeek) {}

    @PostMapping
    public DailyTaskDto create(@RequestBody CreateDailyTaskRequest req){
        DailyTask created = service.createDailyTask(req.title(), req.description(), req.difficulty(), req.startDate(), req.recurrenceType(), req.repeatEvery(), req.daysOfWeek());
        return DailyTaskMapper.toDto(created);
    }

    @GetMapping
    public List<DailyTaskDto> list() {
        return service.findAll()
                .stream()
                .map(DailyTaskMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyTaskDto> updateDailyTask(@PathVariable UUID id, @RequestBody DailyTask dailyTask) {
        try {
            DailyTask updated = service.updateDailyTask(id, dailyTask);
            return ResponseEntity.ok(DailyTaskMapper.toDto(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyTask(@PathVariable UUID id) {
        service.deleteDailyTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<DailyTaskDto> completeDailyTask(@PathVariable UUID id) {
        try {
            DailyTask completed = service.completeDailyTask(id);
            return ResponseEntity.ok(DailyTaskMapper.toDto(completed));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
