package com.errandGo.backend.controller;

import com.errandGo.backend.entities.Report;
import com.errandGo.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // ✅ Get all reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    // ✅ Get a report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    // ✅ Create a new report
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }

    // ✅ Delete a report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get all reports submitted by a specific reporter
    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<List<Report>> getReportsByReporter(@PathVariable Long reporterId) {
        return ResponseEntity.ok(reportService.getReportsByReporter(reporterId));
    }

    // ✅ Get all reports filed against a specific account
    @GetMapping("/reported/{reportedId}")
    public ResponseEntity<List<Report>> getReportsAgainst(@PathVariable Long reportedId) {
        return ResponseEntity.ok(reportService.getReportsAgainst(reportedId));
    }
}
