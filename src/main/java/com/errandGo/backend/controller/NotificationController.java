package com.errandGo.backend.controller;

import com.errandGo.backend.entities.Notification;
import com.errandGo.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // GET all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // GET a single notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    // GET all notifications for a specific account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Notification>> getNotificationsByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(notificationService.getNotificationsByAccountId(accountId));
    }

    // POST: Send a new notification to an account
    @PostMapping
    public ResponseEntity<Notification> sendNotification(
            @RequestParam Long accountId,
            @RequestParam String message
    ) {
        return ResponseEntity.ok(notificationService.sendNotification(accountId, message));
    }

    // PUT: Mark notification as seen
    @PutMapping("/{id}/seen")
    public ResponseEntity<Notification> markAsSeen(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsSeen(id));
    }

    // DELETE: Remove a notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
