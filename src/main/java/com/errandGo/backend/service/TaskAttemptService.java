package com.errandGo.backend.service;

import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.entities.Task;
import com.errandGo.backend.entities.TaskAttempt;
import com.errandGo.backend.repositories.ErrandBoyRepository;
import com.errandGo.backend.repositories.TaskAttemptRepository;
import com.errandGo.backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskAttemptService {

    private final TaskAttemptRepository taskAttemptRepository;
    private final TaskRepository taskRepository;
    private final ErrandBoyRepository errandBoyRepository;

    // ✅ Create a task attempt
    @Transactional
    public TaskAttempt createAttempt(Long taskId, Long errandBoyId, boolean wasAccepted) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        ErrandBoy errandBoy = errandBoyRepository.findById(errandBoyId)
                .orElseThrow(() -> new IllegalArgumentException("Errand Boy not found"));

        TaskAttempt attempt = TaskAttempt.builder()
                .task(task)
                .errandBoy(errandBoy)
                .wasAccepted(wasAccepted)
                .attemptedAt(LocalDateTime.now())
                .build();

        return taskAttemptRepository.save(attempt);
    }

    // ✅ Get all attempts for a task
    public List<TaskAttempt> getAttemptsByTaskId(Long taskId) {
        return taskAttemptRepository.findByTaskId(taskId);
    }

    // ✅ Get all attempts by an errand boy
    public List<TaskAttempt> getAttemptsByErrandBoyId(Long errandBoyId) {
        return taskAttemptRepository.findByErrandBoyId(errandBoyId);
    }

    // ✅ Delete an attempt (optional)
    public void deleteAttempt(Long id) {
        if (!taskAttemptRepository.existsById(id)) {
            throw new IllegalArgumentException("Attempt not found");
        }
        taskAttemptRepository.deleteById(id);
    }
}
