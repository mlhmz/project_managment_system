package de.szut.lf8_project.project.services;

import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.repositories.ProjectRepository;
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

    /**
     * Deletes project by id
     *
     * @param id unique project id
     */
    public void deleteProject(long id) {
        this.repository.deleteById(id);
    }

    /**
     * Checks if the Project exists
     *
     * @param id unique project id
     * @return boolean if project is existing
     */
    public boolean isProjectExisting(long id) {
        return this.repository.existsById(id);
    }
}
