package com.errandGo.backend.service;

import com.errandGo.backend.entities.Report;
import com.errandGo.backend.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    // ✅ Get all reports
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // ✅ Get report by ID
    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
    }

    // ✅ Create a new report
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    // ✅ Delete a report
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new IllegalArgumentException("Report not found");
        }
        reportRepository.deleteById(id);
    }

    // ✅ Get reports by reporter
    public List<Report> getReportsByReporter(Long reporterId) {
        return reportRepository.findByReportedById(reporterId);
    }

    // ✅ Get reports against a person
    public List<Report> getReportsAgainst(Long reportedId) {
        return reportRepository.findByReportedAgainstId(reportedId);
    }
}
