package de.szut.lf8_project.project.dto;

import de.szut.lf8_project.employee.EmployeeReferenceDto;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;

import java.time.LocalDateTime;

@Data
public class GetProjectDto {
    private long id;

    private EmployeeReferenceDto responsibleEmployee;

    private long customerId;

    private String comment;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime endDate;

    private CollectionModel<EmployeeReferenceDto> employeeIds;
}