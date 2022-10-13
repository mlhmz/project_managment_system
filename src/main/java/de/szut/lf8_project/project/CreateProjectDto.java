package de.szut.lf8_project.project;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CreateProjectDto {
    @NotBlank
    private long responsibleEmployeeId;

    @NotBlank
    private long customerId;

    @NotBlank
    @Size(max = 150, message = "The comment shouldn't exceed 150 characters")
    private String comment;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
    private LocalDateTime plannedEndDate;
}
