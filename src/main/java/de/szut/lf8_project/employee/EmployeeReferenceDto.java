package de.szut.lf8_project.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

/**
 * HATEOAS Referenz für Employees
 */
@Getter
@AllArgsConstructor
public class EmployeeReferenceDto extends RepresentationModel<EmployeeReferenceDto> {
    Long id;
}
