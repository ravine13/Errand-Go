package com.errandGo.backend.service;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.Notification;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;


    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // ✅ Get one notification by ID
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
    }

    // ✅ Get all notifications for a specific account
    public List<Notification> getNotificationsByAccountId(Long accountId) {
        return notificationRepository.findByAccountId(accountId);
    }

    public void sendApproval(String to, String loginEmail, String tempPassword) {
        log.warn("[DEV ONLY] To {}: approved. Login: {} Temporary password: {}", to, loginEmail, tempPassword);
    }

    public void sendRejection(String to, String reason) {
        log.info("To {}: your errand boy application was rejected. Reason: {}", to, reason);
    }

    // ✅ Create and send a notification
    public Notification sendNotification(Long accountId, String message) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Notification notification = Notification.builder()
                .account(account)
                .message(message)
                .seen(false)
                .build();

        return notificationRepository.save(notification);
    }

    // ✅ Mark a notification as seen
    @Transactional
    public Notification markAsSeen(Long id) {
        Notification notification = getNotificationById(id);
        notification.setSeen(true);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Notification not found");
        }
        notificationRepository.deleteById(id);
    }
}
