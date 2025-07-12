package com.errandGo.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminReportDTO {
    private BigDecimal totalAmountCollected;
    private String mostActiveDay;
    private String leastActiveDay;
    private long totalTasks;
    private Map<String, Long> tasksPerDay;
}
