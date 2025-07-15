package com.errandGo.backend.controller;

import com.errandGo.backend.dto.AdminReportDTO;
import com.errandGo.backend.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final AnalyticsService analyticService;

    public ResponseEntity<AdminReportDTO> getAdminReport() {
        return ResponseEntity.ok(new AdminReportDTO());

    }
}
