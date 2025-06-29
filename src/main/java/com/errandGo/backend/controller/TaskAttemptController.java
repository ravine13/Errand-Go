package com.errandGo.backend.controller;

import com.errandGo.backend.entities.TaskAttempt;
import com.errandGo.backend.service.TaskAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-attempts")
@RequiredArgsConstructor
public class TaskAttemptController {

    private final TaskAttemptService taskAttemptService;

    // ✅ Create a task attempt
    @PostMapping
    public ResponseEntity<TaskAttempt> createAttempt(
            @RequestParam Long taskId,
            @RequestParam Long errandBoyId,
            @RequestParam boolean wasAccepted
    ) {
        TaskAttempt attempt = taskAttemptService.createAttempt(taskId, errandBoyId, wasAccepted);
        return ResponseEntity.ok(attempt);
    }

    // ✅ Get all attempts for a task
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskAttempt>> getAttemptsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskAttemptService.getAttemptsByTaskId(taskId));
    }

    // ✅ Get all attempts by an errand boy
    @GetMapping("/errand-boy/{errandBoyId}")
    public ResponseEntity<List<TaskAttempt>> getAttemptsByErrandBoyId(@PathVariable Long errandBoyId) {
        return ResponseEntity.ok(taskAttemptService.getAttemptsByErrandBoyId(errandBoyId));
    }

    // ✅ Delete a task attempt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttempt(@PathVariable Long id) {
        taskAttemptService.deleteAttempt(id);
        return ResponseEntity.noContent().build();
    }
}
