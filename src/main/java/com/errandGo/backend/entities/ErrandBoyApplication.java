package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import model.ApplicationStatus;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class ErrandBoyApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Account applicant;

    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    // stored filenames, never original names
    private String idDocumentFile;
    private String goodConductFile;
    private String selfieFile;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(length = 500)
    private String rejectionReason;

    private String reviewedBy;
    private Instant reviewedAt;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @OneToOne
    private Account createdAccount; // set on approval
}

