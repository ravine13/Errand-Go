package com.errandGo.backend.controller;

import com.errandGo.backend.dto.AdminReportDTO;
import com.errandGo.backend.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/reports")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {

    private final AnalyticsService analyticsService;

    @GetMapping("/filter")
    public ResponseEntity<AdminReportDTO> getFilteredReport(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        AdminReportDTO report = analyticsService.generateAdminReport(start, end);
        return ResponseEntity.ok(report);
    }
}
