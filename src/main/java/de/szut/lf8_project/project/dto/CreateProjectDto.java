package de.szut.lf8_project.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CreateProjectDto {
    @Range(min = 1)
    private long responsibleEmployeeId;

    @Range(min = 1)
    private long customerId;

    @NotEmpty
    @Size(max = 150, message = "The comment shouldn't exceed 150 characters")
    private String comment;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd_HH-mm-ss")
    @NotNull(message = "The startDate is mandatory")
    private LocalDateTime startDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd_HH-mm-ss")
    @NotNull(message = "The plannedEndDate is mandatory")
    private LocalDateTime plannedEndDate;
}
