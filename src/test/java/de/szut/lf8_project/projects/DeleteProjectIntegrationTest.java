package de.szut.lf8_project.projects;

import de.szut.lf8_project.AuthorizedIT;
import de.szut.lf8_project.project.entities.Project;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


public class DeleteProjectIntegrationTest extends AuthorizedIT {
    @Test
    public void deleteProjectIT() throws Exception {
        Project project = Project.builder()
                .responsibleEmployeeId(9L)
                .customerId(1L)
                .startDate(LocalDateTime.of(2022, 12, 24, 12, 0, 0))
                .plannedEndDate(LocalDateTime.of(2022, 12, 27, 23, 59, 59))
                .comment("This is a new project!")
                .build();

        project = projectRepository.save(project);

        this.mockMvc.perform(delete("/project/{id}", project.getId()));
        final var loadedEntity = projectRepository.findById(project.getId()).isPresent();
        assertThat(loadedEntity, is(false));
    }
}
