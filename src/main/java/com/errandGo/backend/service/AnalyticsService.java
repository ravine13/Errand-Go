package com.errandGo.backend.service;

import com.errandGo.backend.dto.AdminReportDTO;
import com.errandGo.backend.repositories.TaskRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final TaskRepository taskRepository;

    public AnalyticsService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public AdminReportDTO generateAdminReport(LocalDateTime start, LocalDateTime end) {
        BigDecimal totalAmount = taskRepository.getTotalAmountCollected();
        List<Object[]> taskByDay = taskRepository.getTasksGroupedByDayBetween(start, end);

        AdminReportDTO report = new AdminReportDTO();
        report.setTotalAmountCollected(totalAmount != null ? totalAmount : BigDecimal.ZERO);

        if (!taskByDay.isEmpty()) {
            report.setMostActiveDay((String) taskByDay.get(0)[0]);
            report.setLeastActiveDay((String) taskByDay.get(taskByDay.size() - 1)[0]);
        }

        Map<String, Long> taskMap = taskByDay.stream()
                .collect(Collectors.toMap(r -> (String) r[0], r -> ((Number) r[1]).longValue()));

        report.setTasksPerDay(taskMap);
        report.setTotalTasks(taskMap.values().stream().mapToLong(Long::longValue).sum());

        return report;
    }

    public ByteArrayInputStream exportReportToExcel(AdminReportDTO report) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Admin Report");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Tasks");

            int rowNum = 1;
            for (Map.Entry<String, Long> entry : report.getTasksPerDay().entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            int summaryStart = rowNum + 2;

            sheet.createRow(summaryStart).createCell(0).setCellValue("Total Tasks");
            sheet.getRow(summaryStart).createCell(1).setCellValue(report.getTotalTasks());

            sheet.createRow(summaryStart + 1).createCell(0).setCellValue("Total Amount Collected");
            sheet.getRow(summaryStart + 1).createCell(1).setCellValue(report.getTotalAmountCollected().doubleValue());

            sheet.createRow(summaryStart + 2).createCell(0).setCellValue("Most Active Day");
            sheet.getRow(summaryStart + 2).createCell(1).setCellValue(report.getMostActiveDay());

            sheet.createRow(summaryStart + 3).createCell(0).setCellValue("Least Active Day");
            sheet.getRow(summaryStart + 3).createCell(1).setCellValue(report.getLeastActiveDay());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
