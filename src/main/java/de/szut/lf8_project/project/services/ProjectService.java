package de.szut.lf8_project.project.services;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.dto.ChangeProjectDto;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project createProject(Project project) {
        return this.repository.save(project);
    }

    public Project updateProject(ChangeProjectDto dto, Long id){
        Optional<Project> optProject = this.repository.findById(id);

        if(optProject.isPresent()){
            return updateProjectData(dto, optProject.get());
        } else{
            throw new ResourceNotFoundException(String.format("No Project was found for %d",id));
        }
    }

    private Project updateProjectData(ChangeProjectDto dto, Project project) {
        if(!dto.getComment().isEmpty()){
            project.setComment(dto.getComment());
        }
        if(dto.getPlannedEndDate() != null){
            project.setPlannedEndDate(dto.getPlannedEndDate());
        }
        if(dto.getResponsibleEmployeeId() != null){
            project.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        }
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
  
     * Holt alle Projekte
     *
     * @return alle Projekte die in der Datenbank existieren
     */
    public List<Project> readAllProjects() {
        return this.repository.findAll();
    }

    /**
     * Gets a {@link Project} with a certain id
     *
     * @param projectId unique id of the Project {@link Project#getId()}
     * @return The project with the unique id
     * @throws ResourceNotFoundException thrown when the project doesn't exist
     */
    public Project readProjectById(long projectId) {
        Optional<Project> project = this.repository.findById(projectId);

        if (project.isEmpty()) {
            throw new ResourceNotFoundException(String.format("The project with the id '%d' was not found.", projectId));
        }

        return project.get();
    }

    /**
     * Gets all Project where a customer is involved
     *
     * @param customerId The customers id
     * @return A {@link List<Project>} with the projects.
     */
    public List<Project> readProjectsByCustomerId(long customerId) {
        return this.repository.getProjectsByCustomerId(customerId);
    }

    /**
     * Gets all {@link Project} of a responsible Employee ({@link Project#getEmployeeIds()})
     *
     * @param employeeId The responsible Employee
     * @return {@link List<Project>} of Projects in which the Employee ({@link Project#getResponsibleEmployeeId()})
     * is responsive for. If the employee is not responsible for any project, null will be returned
     */
    public List<Project> readProjectsByResponsibleEmployeeId(long employeeId) {
        return this.repository.getProjectsByResponsibleEmployeeId(employeeId);
    }

    /**
     * Gets {@link Set<ProjectEmployee>} of a {@link Project}
     *
     * @param projectId The {@link Project}'s id which employees be received
     * @return Returns a list with id's of every employee
     */
    public Set<ProjectEmployee> readAllEmployeesFromProject(long projectId) {
        Project project = readProjectById(projectId);
        return project.getEmployeeIds();
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
