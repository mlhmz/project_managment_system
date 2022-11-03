package de.szut.lf8_project.project.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class GetProjectDto {
    private long id;

    private long responsibleEmployeeId;

    private long customerId;

    private String comment;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime endDate;

    private Set<Integer> employeeIds;
}