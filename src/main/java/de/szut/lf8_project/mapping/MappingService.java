package de.szut.lf8_project.mapping;

import de.szut.lf8_project.employee.EmployeeReferenceDto;
import de.szut.lf8_project.employee.EmployeeReferenceModelAssembler;
import de.szut.lf8_project.employee.GetEmployeeReferencesDto;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MappingService {
    EmployeeReferenceModelAssembler employeeReferenceModelAssembler = new EmployeeReferenceModelAssembler();

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

    public List<GetProjectDto> mapProjectsToGetProjectList(List<Project> projects) {
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

        dto.setResponsibleEmployee(mapProjectEmployeeToEmployeeReference(
                buildProjectEmployeeById(project.getResponsibleEmployeeId())
        ));
        dto.setEmployeeIds(mapProjectEmployeesToEmployeeReferences(project.getEmployeeIds()));

        return dto;
    }

    public CollectionModel<EmployeeReferenceDto> mapProjectEmployeesToEmployeeReferences(Set<ProjectEmployee> projectEmployees) {
        if (projectEmployees != null && !projectEmployees.isEmpty()) {
            return this.employeeReferenceModelAssembler.toCollectionModel(projectEmployees);
        } else {
            return CollectionModel.empty();
        }
    }

    public GetEmployeeReferencesDto mapProjectEmployeesToGetEmployeeReferencesDto(Set<ProjectEmployee> projectEmployees) {
        return new GetEmployeeReferencesDto(mapProjectEmployeesToEmployeeReferences(projectEmployees));
    }

    public EmployeeReferenceDto mapProjectEmployeeToEmployeeReference(ProjectEmployee employee) {
        return this.employeeReferenceModelAssembler.toModel(employee);
    }

    private ProjectEmployee buildProjectEmployeeById(@NotNull long responsibleEmployeeId) {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setEmployeeId(responsibleEmployeeId);
        return projectEmployee;
    }
}
