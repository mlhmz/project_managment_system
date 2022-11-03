package de.szut.lf8_project.mapping;

import de.szut.lf8_project.employee.EmployeeReferenceModelAssembler;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.entities.Project;
import org.springframework.stereotype.Service;

import java.util.List;
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
        dto.setStartDate(project.getStartDate());
        dto.setPlannedEndDate(project.getPlannedEndDate());
        dto.setEndDate(project.getEndDate());

        EmployeeReferenceModelAssembler employeeReferenceModelAssembler = new EmployeeReferenceModelAssembler();
        dto.setEmployeeIds(employeeReferenceModelAssembler.toCollectionModel(project.getEmployeeIds()));

        return dto;
    }
}
