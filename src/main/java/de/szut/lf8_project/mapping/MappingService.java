package de.szut.lf8_project.mapping;

import de.szut.lf8_project.project.controllers.ProjectEmployee;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.entities.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MappingService {
    /**
     * Mappt erforderliche Informationen des {@link CreateProjectDto} zu {@link Project}
     */
    public Project mapCreateProjectDtoToProject(CreateProjectDto dto) {
        return Project.builder()
                .responsibleEmployeeId(dto.getResponsibleEmployeeId())
                .customerId(dto.getCustomerId())
                .comment(dto.getComment())
                .startDate(dto.getStartDate())
                .plannedEndDate(dto.getPlannedEndDate())
                .build();
    }

    public List<GetProjectDto> mapProjectsToGetProjectDtos(List<Project> projects) {
        return projects.stream()
                .map(this::mapProjectToGetProjectDto)
                .collect(Collectors.toList());
    }

    public GetProjectDto mapProjectToGetProjectDto(Project project) {
        GetProjectDto dto = new GetProjectDto();
        dto.setId(project.getId());
        dto.setComment(project.getComment());
        dto.setCustomerId(project.getCustomerId());
        dto.setResponsibleEmployeeId(project.getResponsibleEmployeeId());
        dto.setStartDate(project.getStartDate());
        dto.setPlannedEndDate(project.getPlannedEndDate());
        dto.setEndDate(project.getEndDate());

        Set<Long> employeeIds = project.getEmployeeIds().stream()
                .map(ProjectEmployee::getEmployeeId)
                .collect(Collectors.toSet());
        dto.setEmployeeIds(employeeIds);

        return dto;
    }
}
