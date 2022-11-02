package de.szut.lf8_project.employee;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class EmployeeService {
    private static final String EMPLOYEE_URL = "https://employee.szut.dev/employees";
    private final RestTemplate template;
    private final QualificationService qualificationService;

    public EmployeeService() {
        template = new RestTemplate();
        qualificationService = new QualificationService();
    }

    /**
     * Fragt an Employee-API Qualification und Employee an
     *
     * @param id Id des Employees
     * @param qualification Qualification die abgefragt werden soll
     * @return boolean ob Employee qualification besitzt
     */
    public boolean isEmployeeOwningCertainQualification(long id, String qualification, String bearerToken) {
        EmployeeDto dto = getEmployee(id, bearerToken);
        if (qualificationService.isQualificationExisting(qualification, bearerToken)) {
            return Arrays.asList(dto.getSkillSet()).contains(qualification);
        } else {
            throw new ResourceNotFoundException(String.format("The qualification %s doesn't exist.", qualification));
        }
    }

    public boolean isEmployeeExisting(long id, String bearerToken) {
        try {
            RequestEntity<Void> request = RequestEntity.get(EMPLOYEE_URL + "/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", bearerToken)
                    .build();

            ResponseEntity<EmployeeDto> employeeEntity =
                    template.exchange(request, EmployeeDto.class);

            return employeeEntity.getBody() != null;
        } catch (HttpClientErrorException.NotFound ex) {
            return false;
        }

    }

    /**
     * Holt Employee von Rest-Service
     *
     * @throws ResourceNotFoundException wenn Mitarbeiter nicht existiert
     */
    public EmployeeDto getEmployee(long id, String bearerToken) {
        try {
            RequestEntity<Void> request = RequestEntity.get(EMPLOYEE_URL + "/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", bearerToken)
                    .build();

            ResponseEntity<EmployeeDto> employeeEntity =
                    template.exchange(request, EmployeeDto.class);

            return employeeEntity.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException(String.format("The employee with the id %d couldn't be found.", id));
        }
    }
}
