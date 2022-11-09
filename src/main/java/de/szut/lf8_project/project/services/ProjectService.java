package de.szut.lf8_project.project.services;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.controllers.ProjectEmployee;
import de.szut.lf8_project.project.repositories.ProjectEmployeeRepository;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import de.szut.lf8_project.project.entities.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectEmployeeRepository projectEmployeeRepository;

    public ProjectService(ProjectRepository repository, ProjectEmployeeRepository projectEmployeeRepository) {
        this.repository = repository;
        this.projectEmployeeRepository = projectEmployeeRepository;
    }

    public Project createProject(Project project) {
        return this.repository.save(project);
    }

    public boolean isEmployeeInvolvedInProject(long projectId, long employeeId){
        Optional<Project> oProject = this.repository.findById(projectId);
        if(oProject.isPresent()){
            Optional<ProjectEmployee> oEmployee =
                    this.projectEmployeeRepository.findProjectEmployeeByProjectIdAndEmployeeId(projectId, employeeId);
                return oEmployee.isPresent();
        } else {
            // TODO: 400 Bad Request wenn employee nicht in Project beteiligt ist.
            throw new ResourceNotFoundException(String.format("The project with the id %d couldn't be found.",
                    projectId));
        }
    }

    public boolean deleteEmployeeFromProject(long projectId, long employeeId){
        return this.projectEmployeeRepository.deleteProjectEmployeeByProjectIdAndEmployeeId(projectId,employeeId);
    }
}
