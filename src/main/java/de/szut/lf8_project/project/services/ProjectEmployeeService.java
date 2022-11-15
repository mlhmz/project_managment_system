package de.szut.lf8_project.project.services;

import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import de.szut.lf8_project.project.repositories.ProjectEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectEmployeeService {
    private final ProjectEmployeeRepository projectEmployeeRepository;

    public ProjectEmployeeService(ProjectEmployeeRepository projectEmployeeRepository) {
        this.projectEmployeeRepository = projectEmployeeRepository;
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
