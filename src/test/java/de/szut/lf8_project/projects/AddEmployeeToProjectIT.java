package de.szut.lf8_project.projects;

import de.szut.lf8_project.AuthorizedIT;
import de.szut.lf8_project.project.entities.Project;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddEmployeeToProjectIT extends AuthorizedIT {

    @Test
    public void addEmployeeToProject() throws Exception {
        Project project = Project.builder()
                .responsibleEmployeeId(9L)
                .customerId(1L)
                .comment("This is a new project!")
                .startDate(LocalDateTime.of(2022, 12, 24, 12, 0, 0))
                .plannedEndDate(LocalDateTime.of(2022, 12, 27, 23, 59, 59))
                .build();

        project = projectRepository.save(project);

        this.mockMvc.perform(
                put("/project/{id}/add/employee/{employeeId}?qualification={qualification}",
                        project.getId(), 10, "Java")
                        .header(HttpHeaders.AUTHORIZATION, fetchJWT()))
                .andExpect(status().isOk());

        final var loadedEntity = projectEmployeeRepository.existsProjectEmployeeByProjectIdAndEmployeeId(project.getId(), 10);

        assertThat(loadedEntity, is(true));
    }
}
