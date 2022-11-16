package de.szut.lf8_project.employee;

import lombok.Data;

import java.util.List;

@Data
public class GetAllQualificationsDto {
    private List<GetQualificationDto> qualificationList;
}
