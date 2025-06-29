package com.errandGo.backend.service;

import com.errandGo.backend.entities.Task;
import com.errandGo.backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // ✅ Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ✅ Get a task by ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    // ✅ Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // ✅ Update task
    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setLocation(updatedTask.getLocation());
        existingTask.setLatitude(updatedTask.getLatitude());
        existingTask.setLongitude(updatedTask.getLongitude());
        existingTask.setAcceptedAt(updatedTask.getAcceptedAt());
        existingTask.setStartedAt(updatedTask.getStartedAt());
        existingTask.setCompletedAt(updatedTask.getCompletedAt());
        existingTask.setRejectionReason(updatedTask.getRejectionReason());
        existingTask.setUser(updatedTask.getUser());
        existingTask.setErrandBoy(updatedTask.getErrandBoy());
        existingTask.setCategory(updatedTask.getCategory());

        return taskRepository.save(existingTask);
    }

    // ✅ Delete task
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // ✅ Get tasks by user ID
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    // ✅ Get tasks by errand boy ID
    public List<Task> getTasksByErrandBoyId(Long errandBoyId) {
        return taskRepository.findByErrandBoyId(errandBoyId);
    }
}
