package de.szut.lf8_project.project.services;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.controllers.ProjectEmployee;
import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project createProject(Project project) {
        return this.repository.save(project);
    }

    public Project addProjectEmployeeToProject(Long projectId, Long employeeId){
        Optional<Project> response = repository.findById(projectId);

        if (response.isEmpty()) {
            throw new ResourceNotFoundException(String.format("The Project with the Id %d couldn't found", projectId));
        }
        Project project = response.get();
        ProjectEmployee projectEmployee = buildProjectEmployee(project, employeeId);
        project.getEmployeeIds().add(projectEmployee);

        return this.repository.save(project);
    }

    /**
     * TODO: Api Doc
     *
     * @param project
     * @param employeeId
     * @return
     */
    private ProjectEmployee buildProjectEmployee(Project project, Long employeeId) {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setProject(project);
        projectEmployee.setEmployeeId(employeeId);
        return projectEmployee;
    }
}
