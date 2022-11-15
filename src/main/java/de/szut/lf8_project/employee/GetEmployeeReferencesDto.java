package de.szut.lf8_project.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;

/**
 * List of {@link EmployeeReferenceDto}
 * This class was created because exposing the {@link EmployeeReferenceDto} to the API consumer will result in
 * exposing the name "employeeReferenceDtoList". The name "employeeReferences" fits better for the consumer.
 */
@Data
@AllArgsConstructor
public class GetEmployeeReferencesDto {
    CollectionModel<EmployeeReferenceDto> employeeReferences;
}
