package de.szut.lf8_project.project.services;

import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import de.szut.lf8_project.project.repositories.ProjectEmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectEmployeeService {
    private final ProjectEmployeeRepository projectEmployeeRepository;

    public ProjectEmployeeService(ProjectEmployeeRepository projectEmployeeRepository) {
        this.projectEmployeeRepository = projectEmployeeRepository;
    }

    /**
     * Checks if Employee is available in Span with Employee ID
     *
     * @param employeeId The id of the employee that shall be checked
     * @param startDate the start date of the span
     * @param plannedEndDate the end date of the span
     * @return boolean if the employee is available in the timespan
     */
    public boolean isEmployeeAvailableInTimespan(long employeeId, LocalDateTime startDate, LocalDateTime plannedEndDate) {
        List<ProjectEmployee> projectEmployee = projectEmployeeRepository.getProjectEmployeesByEmployeeId(employeeId);

        for (ProjectEmployee employee : projectEmployee) {
            if (isProjectEmployeeDateColliding(startDate, plannedEndDate, employee)) return false;
        }

        return true;
    }

    /**
     * When Project Employee is in the timespan
     *
     * @return boolean if the project employee is in span with start and end date
     */
    public boolean isProjectEmployeeDateColliding(LocalDateTime startDate, LocalDateTime endDate, ProjectEmployee employee) {
        Project project = employee.getProject();
        LocalDateTime projectStartDate = project.getStartDate();
        LocalDateTime projectEndDate = project.getPlannedEndDate();

        return (startDate.isAfter(projectStartDate) && startDate.isBefore(projectEndDate)) ||
                (endDate.isAfter(projectStartDate) && endDate.isBefore(projectEndDate)
                || projectStartDate.isAfter(startDate) && projectStartDate.isBefore(endDate) ||
                        projectEndDate.isAfter(endDate) && projectEndDate.isBefore(endDate));
    }

    /**
     * Removes Employee from {@link Project} and deletes {@link ProjectEmployee} Entity
     *
     * @param projectId ID of the {@link Project}
     * @param employeeId ID of the Employee
     * @return callback if the Employee was removed
     */
    public boolean removeEmployeeFromProject(long projectId, long employeeId){
        return this.projectEmployeeRepository.deleteProjectEmployeeByProjectIdAndEmployeeId(projectId,employeeId);
    }

    /**
     * Checks if the Employee is involved in the Project
     *
     * @param projectId ID of the Project
     * @param employeeId ID of the Employee
     * @return callback if the Employee is involved
     */
    public boolean isEmployeeInvolvedInProject(long projectId, long employeeId){
        return this.projectEmployeeRepository.existsProjectEmployeeByProjectIdAndEmployeeId(projectId, employeeId);
    }

}
