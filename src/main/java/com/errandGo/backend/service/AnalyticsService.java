package com.errandGo.backend.service;

import com.errandGo.backend.dto.AdminReportDTO;
import com.errandGo.backend.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final TaskRepository taskRepository;

    public AnalyticsService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public AdminReportDTO generateAdminReport() {

        AdminReportDTO report = new AdminReportDTO();


        BigDecimal totalAmount = taskRepository.getTotalAmountCollected();
        report.setTotalAmountCollected(totalAmount != null ? totalAmount : BigDecimal.ZERO);

        List<Object[]> taskByDay = taskRepository.getTasksGroupedByDay();

        if (!taskByDay.isEmpty()) {
            report.setMostActiveDay((String) taskByDay.get(0)[0]);
            report.setLeastActiveDay((String) taskByDay.get(taskByDay.size() - 1)[0]);
        }

        Map<String, Long> taskMap = taskByDay.stream().collect(Collectors.toMap(
                r -> (String) r[0],
                r -> ((Number) r[1]).longValue()
        ));

        report.setTasksPerDay(taskMap);
        report.setTotalTasks(taskMap.values().stream().mapToLong(Long::longValue).sum());

        return report;
    }
}
