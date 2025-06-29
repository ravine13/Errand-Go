package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.id = :id")
    Optional<Report> findById(@Param("id") Long id);

    List<Report> findByReportedById(Long reporterId);

    List<Report> findByReportedAgainstId(Long reportedId);

}
