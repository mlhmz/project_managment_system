package de.szut.lf8_project.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;

@Data
@AllArgsConstructor
public class GetEmployeeReferencesDto {
    CollectionModel<EmployeeReferenceDto> employeeReferences;
}
