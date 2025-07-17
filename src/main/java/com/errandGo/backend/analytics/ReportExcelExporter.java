package com.errandGo.backend.export;

import com.errandGo.backend.dto.AdminReportDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Component
public class ReportExcelExporter {

    public ByteArrayInputStream exportToExcel(AdminReportDTO report) throws IOException {
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
