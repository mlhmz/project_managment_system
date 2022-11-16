package de.szut.lf8_project.projects;

import de.szut.lf8_project.AuthorizedIT;
import de.szut.lf8_project.project.entities.Project;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReadProjectInformationIT extends AuthorizedIT {
    @Test
    public void readProjectById() throws Exception {
        Project project = Project.builder()
                .responsibleEmployeeId(9L)
                .customerId(1L)
                .startDate(LocalDateTime.of(2022, 12, 24, 12, 0, 0))
                .plannedEndDate(LocalDateTime.of(2022, 12, 27, 23, 59, 59))
                .comment("This is a new project!")
                .build();

        project = projectRepository.save(project);

        this.mockMvc.perform(get("/project/{id}", project.getId())
                .header(HttpHeaders.AUTHORIZATION, fetchJWT()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("responsibleEmployee.id", is(9)))
                .andExpect(jsonPath("customerId", is(1)))
                .andExpect(jsonPath("comment", is("This is a new project!")))
                .andExpect(jsonPath("startDate", is("2022-12-24T12:00:00")))
                .andExpect(jsonPath("plannedEndDate", is("2022-12-27T23:59:59")));
    }
}
