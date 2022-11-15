package de.szut.lf8_project.mapping;

import de.szut.lf8_project.employee.EmployeeReferenceDto;
import de.szut.lf8_project.employee.EmployeeReferenceModelAssembler;
import de.szut.lf8_project.employee.GetEmployeeReferencesDto;
import de.szut.lf8_project.project.dto.ChangeProjectDto;
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

    /**
     * Maps a List of Project to a list of {@link GetProjectDto}
     *
     * @param projects object to map
     * @return {@link List} of {@link GetProjectDto}
     */
    public List<GetProjectDto> mapProjectsToGetProjectList(List<Project> projects) {
        return projects.stream()
                .map(this::mapProjectToGetProjectDto)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Project to GetProjectDto
     *
     * @param project Data to fill into the {@link GetProjectDto}
     * @return GetProjectDto with filled Data
     */
    public GetProjectDto mapProjectToGetProjectDto(Project project) {
        GetProjectDto dto = new GetProjectDto();
        dto.setId(project.getId());
        dto.setComment(project.getComment());
        dto.setCustomerId(project.getCustomerId());
        dto.setStartDate(project.getStartDate());
        dto.setPlannedEndDate(project.getPlannedEndDate());
        dto.setEndDate(project.getEndDate());

        dto.setResponsibleEmployee(mapProjectEmployeeToEmployeeReference(
                buildProjectEmployee(project.getResponsibleEmployeeId())
        ));
        dto.setEmployeeIds(mapProjectEmployeesToEmployeeReferences(project.getEmployeeIds()));

        return dto;
    }

    /**
     * Maps ProjectEmployee to a Set of {@link EmployeeReferenceDto}
     *
     * @param projectEmployees set of objects to map into references
     * @return HATEOAS CollectionModel with EmployeeReferences
     */
    public CollectionModel<EmployeeReferenceDto> mapProjectEmployeesToEmployeeReferences(Set<ProjectEmployee> projectEmployees) {
        if (projectEmployees != null && !projectEmployees.isEmpty()) {
            return this.employeeReferenceModelAssembler.toCollectionModel(projectEmployees);
        } else {
            return CollectionModel.empty();
        }
    }

    /**
     * Maps a Set of {@link ProjectEmployee} to {@link GetEmployeeReferencesDto}.
     *
     * @param projectEmployees objects to map
     * @return GetEmployeeReferencesDto filled with ProjectEmployee's
     */
    public GetEmployeeReferencesDto mapProjectEmployeesToGetEmployeeReferencesDto(Set<ProjectEmployee> projectEmployees) {
        return new GetEmployeeReferencesDto(mapProjectEmployeesToEmployeeReferences(projectEmployees));
    }


    /**
     * Maps a {@link ChangeProjectDto} into a {@link Project}
     *
     * @param dto DTO with optional Data
     * @param project Project which should recieve the Update
     * @return updated Project
     */
    public Project mapUpdateProjectDtoIntoProject(ChangeProjectDto dto, Project project) {
        if (!dto.getComment().isEmpty()) {
            project.setComment(dto.getComment());
        }
        if (dto.getPlannedEndDate() != null) {
            project.setPlannedEndDate(dto.getPlannedEndDate());
        }
        if (dto.getResponsibleEmployeeId() != null) {
            project.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        }
        return project;
    }

    /**
     * Maps {@link ProjectEmployee} to an EmployeeReferenceDto
     *
     * @param employee object to map
     * @return Dto filled with ProjectEmployee
     */
    public EmployeeReferenceDto mapProjectEmployeeToEmployeeReference(ProjectEmployee employee) {
        return this.employeeReferenceModelAssembler.toModel(employee);
    }

    /**
     * Builds a ProjectEmployee with an Employee ID
     * Overload of {@link #buildProjectEmployee(long, Project)}
     *
     * @param employeeId Employee ID to put into the ProjectEmployee
     * @return ProjectEmployee with ID
     */
    public ProjectEmployee buildProjectEmployee(@NotNull long employeeId) {
        return buildProjectEmployee(employeeId, null);
    }

    /**
     * Builds a {@link ProjectEmployee} with an Employee ID and a {@link Project}
     *
     * @param employeeId Employee ID to put into the {@link ProjectEmployee}
     * @param project actual Project entity to put into the {@link ProjectEmployee}
     * @return {@link ProjectEmployee} Object
     */
    public ProjectEmployee buildProjectEmployee(@NotNull long employeeId, Project project) {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setEmployeeId(employeeId);
        projectEmployee.setProject(project);
        return projectEmployee;
    }
}
