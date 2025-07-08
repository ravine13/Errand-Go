package com.errandGo.backend.service;

import com.errandGo.backend.entities.Task;
import com.errandGo.backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final PaymentService paymentService;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

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

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getTasksByErrandBoyId(Long errandBoyId) {
        return taskRepository.findByErrandBoyId(errandBoyId);
    }

    @Transactional
    public void completeTask(Long taskId, String paymentMethod, String transactionId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(Task.Status.COMPLETED);
        task.setCompletedAt(LocalDateTime.now());
        taskRepository.save(task);

        BigDecimal amount = task.getAmount();
        paymentService.createPayment(taskId, amount, paymentMethod, transactionId);
    }
}
