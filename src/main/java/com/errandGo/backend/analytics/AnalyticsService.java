package com.errandGo.backend.analytics;

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

    public ByteArrayInputStream exportReportToExcel(AdminReportDTO report) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Admin Report");


            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Metric");
            header.createCell(1).setCellValue("Value");

            int rowIdx = 1;

            sheet.createRow(rowIdx++).createCell(1).setCellValue(report.getTotalAmountCollected().toString());
            sheet.getRow(1).createCell(0).setCellValue("Total Amount Collected");

            sheet.createRow(rowIdx++).createCell(1).setCellValue(report.getTotalTasks());
            sheet.getRow(2).createCell(0).setCellValue("Total Tasks");

            sheet.createRow(rowIdx++).createCell(1).setCellValue(report.getMostActiveDay());
            sheet.getRow(3).createCell(0).setCellValue("Most Active Day");

            sheet.createRow(rowIdx++).createCell(1).setCellValue(report.getLeastActiveDay());
            sheet.getRow(4).createCell(0).setCellValue("Least Active Day");

            // Tasks Per Day section
            Row titleRow = sheet.createRow(rowIdx++);
            titleRow.createCell(0).setCellValue("Day");
            titleRow.createCell(1).setCellValue("Tasks");

            for (Map.Entry<String, Long> entry : report.getTasksPerDay().entrySet()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export Excel report", e);
        }
    }
}
