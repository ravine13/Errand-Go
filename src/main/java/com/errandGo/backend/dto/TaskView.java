package com.errandGo.backend.dto;

import com.errandGo.backend.entities.Task;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TaskView(Long id, String title, String description, Task.Status status, String location,
                       Double latitude, Double longitude, BigDecimal amount, String categoryName,
                       LocalDateTime createdAt, LocalDateTime acceptedAt, LocalDateTime startedAt,
                       LocalDateTime completedAt, String rejectionReason) {

    public static TaskView from(Task t) {
        return new TaskView(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getLocation(),
                t.getLatitude(), t.getLongitude(), t.getAmount(),
                t.getCategory() == null ? null : t.getCategory().getName(),
                t.getCreatedAt(), t.getAcceptedAt(), t.getStartedAt(), t.getCompletedAt(),
                t.getRejectionReason());
    }
}
