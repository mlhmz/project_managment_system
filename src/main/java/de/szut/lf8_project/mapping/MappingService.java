package de.szut.lf8_project.mapping;

import de.szut.lf8_project.project.CreateProjectDto;
import de.szut.lf8_project.project.Project;
import org.springframework.stereotype.Service;

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
}
