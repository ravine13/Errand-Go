package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.ErrandBoyApplication;
import model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ErrandBoyApplicationRepository extends JpaRepository<ErrandBoyApplication, Long> {
    boolean existsByApplicantAndStatus(Account applicant, ApplicationStatus status);
    List<ErrandBoyApplication> findByStatusOrderByCreatedAtAsc(ApplicationStatus status);
    List<ErrandBoyApplication> findByApplicantOrderByCreatedAtDesc(Account applicant);
}