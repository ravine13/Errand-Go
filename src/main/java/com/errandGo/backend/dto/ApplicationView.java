package com.errandGo.backend.dto;

import com.errandGo.backend.entities.ErrandBoyApplication;
import model.ApplicationStatus;

import java.time.Instant;
import java.time.LocalDate;

public record ApplicationView(Long id, String applicantUsername, String fullName, LocalDate dateOfBirth,
                              String phoneNumber, ApplicationStatus status, String rejectionReason,
                              Instant createdAt, Instant reviewedAt, String errandBoyLoginEmail) {

    public static ApplicationView from(ErrandBoyApplication a) {
        return new ApplicationView(a.getId(), a.getApplicant().getUsername(), a.getFullName(),
                a.getDateOfBirth(), a.getPhoneNumber(), a.getStatus(), a.getRejectionReason(),
                a.getCreatedAt(), a.getReviewedAt(),
                a.getCreatedAccount() == null ? null : a.getCreatedAccount().getEmail());
    }
}