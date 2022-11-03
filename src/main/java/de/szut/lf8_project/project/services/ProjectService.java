package de.szut.lf8_project.project.services;

import de.szut.lf8_project.employee.EmployeeDto;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import de.szut.lf8_project.project.entities.Project;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ProjectService {

    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project createProject(Project project) {
        return this.repository.save(project);
    }

    public void deleteProject(long id){ this.repository.deleteById(id);}

    public boolean isProjectExisting(long id){return this.repository.existsById(id);}
}
