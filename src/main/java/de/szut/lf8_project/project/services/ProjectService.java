package de.szut.lf8_project.project.services;

import de.szut.lf8_project.project.repositories.ProjectRepository;
import de.szut.lf8_project.project.entities.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project createProject(Project project) {
        return this.repository.save(project);
    }


}
