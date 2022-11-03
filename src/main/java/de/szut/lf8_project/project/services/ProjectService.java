package de.szut.lf8_project.project.services;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.dto.ChangeProjectDto;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import de.szut.lf8_project.project.entities.Project;
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
}
