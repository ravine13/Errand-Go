package com.errandGo.backend.service;

import com.errandGo.backend.dto.ApplicationView;
import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.entities.ErrandBoyApplication;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.ErrandBoyApplicationRepository;
import com.errandGo.backend.repositories.ErrandBoyRepository;
import lombok.RequiredArgsConstructor;
import model.ApplicationStatus;
import model.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrandBoyApplicationService {

    private final ErrandBoyApplicationRepository applications;
    private final AccountRepository accountRepository;
    private final ErrandBoyRepository errandBoyRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService storage;
    private final NotificationService notifications;

    @Value("${app.errandboy.email-domain:errandboy.com}")
    private String emailDomain;

    @Transactional
    public ApplicationView submit(String username, String fullName, LocalDate dob, String phone,
                                  MultipartFile idDoc, MultipartFile goodConduct, MultipartFile selfie) {
        Account applicant = accountRepository.findByUsername(username).orElseThrow();

        if (applicant.getRole() != Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only users can apply");
        }
        if (fullName == null || fullName.isBlank() || phone == null || phone.isBlank() || dob == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name, date of birth and phone are required");
        }
        if (dob.isAfter(LocalDate.now().minusYears(18))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must be at least 18");
        }
        if (applications.existsByApplicantAndStatus(applicant, ApplicationStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You already have a pending application");
        }

        ErrandBoyApplication app = new ErrandBoyApplication();
        app.setApplicant(applicant);
        app.setFullName(fullName.trim());
        app.setDateOfBirth(dob);
        app.setPhoneNumber(phone.trim());
        app.setIdDocumentFile(storage.store(idDoc, false));
        app.setGoodConductFile(storage.store(goodConduct, false));
        app.setSelfieFile(storage.store(selfie, true));
        return ApplicationView.from(applications.save(app));
    }

    @Transactional(readOnly = true)
    public List<ApplicationView> mine(String username) {
        Account a = accountRepository.findByUsername(username).orElseThrow();
        return applications.findByApplicantOrderByCreatedAtDesc(a).stream().map(ApplicationView::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationView> list(ApplicationStatus status) {
        return applications.findByStatusOrderByCreatedAtAsc(status).stream().map(ApplicationView::from).toList();
    }

    @Transactional(readOnly = true)
    public Resource document(Long id, String type) {
        ErrandBoyApplication app = applications.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String file = switch (type) {
            case "id" -> app.getIdDocumentFile();
            case "good-conduct" -> app.getGoodConductFile();
            case "selfie" -> app.getSelfieFile();
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };
        return storage.load(file);
    }

    @Transactional
    public ApplicationView approve(Long id, String adminName) {
        ErrandBoyApplication app = pending(id);

        String loginEmail = generateLoginEmail(app.getFullName());
        String tempPassword = randomPassword();

        Account account = new Account();
        account.setUsername(loginEmail);
        account.setEmail(loginEmail);
        account.setPassword(passwordEncoder.encode(tempPassword));
        account.setPhoneNumber(app.getPhoneNumber());
        account.setLocation(app.getApplicant().getLocation());
        account.setRole(Role.ERRAND_BOY);
        account.setMustChangePassword(true);
        account = accountRepository.saveAndFlush(account);

        ErrandBoy boy = new ErrandBoy();
        boy.setAccount(account);
        boy.setFullName(app.getFullName());
        errandBoyRepository.save(boy);

        app.setStatus(ApplicationStatus.APPROVED);
        app.setCreatedAccount(account);
        app.setReviewedBy(adminName);
        app.setReviewedAt(Instant.now());

        notifications.sendApproval(app.getApplicant().getEmail(), loginEmail, tempPassword);
        return ApplicationView.from(app);
    }

    @Transactional
    public ApplicationView reject(Long id, String reason, String adminName) {
        if (reason == null || reason.isBlank() || reason.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A reason (max 500 characters) is required");
        }
        ErrandBoyApplication app = pending(id);
        app.setStatus(ApplicationStatus.REJECTED);
        app.setRejectionReason(reason.trim());
        app.setReviewedBy(adminName);
        app.setReviewedAt(Instant.now());

        notifications.sendRejection(app.getApplicant().getEmail(), reason.trim());
        return ApplicationView.from(app);
    }

    private ErrandBoyApplication pending(Long id) {
        ErrandBoyApplication app = applications.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (app.getStatus() != ApplicationStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Application already reviewed");
        }
        return app;
    }

    private String generateLoginEmail(String fullName) {
        String[] parts = Normalizer.normalize(fullName.trim().toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^a-z ]", "").trim().split("\\s+");
        String first = parts[0];
        String last = parts.length > 1 ? parts[parts.length - 1] : parts[0];
        String base = first.charAt(0) + last.substring(0, Math.min(5, last.length()));

        for (int n = 1; ; n++) {
            String candidate = base + n + "@" + emailDomain;
            if (accountRepository.findByEmail(candidate).isEmpty()
                    && accountRepository.findByUsername(candidate).isEmpty()) {
                return candidate;
            }
        }
    }

    private String randomPassword() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
        SecureRandom rnd = new SecureRandom();
        return rnd.ints(14, 0, chars.length()).mapToObj(i -> String.valueOf(chars.charAt(i)))
                .collect(Collectors.joining());
    }
}