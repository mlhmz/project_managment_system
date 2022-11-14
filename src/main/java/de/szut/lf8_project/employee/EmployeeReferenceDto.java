package de.szut.lf8_project.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Reference for Employees, shown as HATEOAS in the API
 *
 * @see RepresentationModel
 * @see <a href="https://en.wikipedia.org/wiki/Hypertext_Application_Language">Wikipedia - Hypertext Application Language</a>
 * @see <a href="https://en.wikipedia.org/wiki/HATEOAS">Wikipedia - HATEOAS</a>
 * @implNote Implementation of this class should succeed with the Content-Type application/hal+json
 */
@Getter
@AllArgsConstructor
public class EmployeeReferenceDto extends RepresentationModel<EmployeeReferenceDto> {
    Long id;
}
