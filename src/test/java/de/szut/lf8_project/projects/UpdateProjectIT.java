package de.szut.lf8_project.projects;

import de.szut.lf8_project.AuthorizedIT;
import de.szut.lf8_project.project.entities.Project;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateProjectIT extends AuthorizedIT {
    @Test
    public void updateProject() throws Exception {
        Project project = new Project();
        project.setResponsibleEmployeeId(10);
        project.setCustomerId(1);
        project.setComment("Sample Project");
        project.setStartDate(LocalDateTime.of(2022, 11, 24, 12, 56, 26));
        project.setPlannedEndDate(LocalDateTime.of(2022, 12, 25, 12, 56, 26));

        project = projectRepository.save(project);

        String content = """
                {
                  "responsibleEmployeeId": 10,
                  "comment": "This is the new project commment!",
                  "plannedEndDate": "2022-12-12_12-56-26"
                }
                """;

                this.mockMvc.perform(put("/project/1")
                        .content(content).contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, fetchJWT()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("responsibleEmployeeId", is(10)))
                .andExpect(jsonPath("comment", is("This is the new project commment!")))
                .andExpect(jsonPath("plannedEndDate", is("2022-12-12T12:56:26")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var loadedEntity = projectRepository.findById(project.getId());

        assertThat(loadedEntity.isPresent(), is(true));
        assertThat(loadedEntity.get().getResponsibleEmployeeId(), equalTo(10L));
        assertThat(loadedEntity.get().getComment(), equalTo("This is the new project commment!"));
        assertThat(loadedEntity.get().getPlannedEndDate(), equalTo(
                LocalDateTime.of(2022, 12, 12, 12, 56, 26)));
    }
}
