package de.szut.lf8_project.projects;

import de.szut.lf8_project.project.entities.ProjectEmployee;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import testcontainers.AbstractIntegrationTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReadEmployeesFromProjectIT extends AbstractIntegrationTest {

    @Test
    public void readEmployeesFromProject() throws Exception {
        String content_project = """
                {
                         "responsibleEmployeeId": "1",
                         "customerId": "1",
                         "comment": "This is a new project!",
                         "startDate": "2022-12-24_12-00-00",
                         "plannedEndDate": "2022-12-27_23-59-59"
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("/project")
                        .content(content_project).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        this.mockMvc.perform(put("/" + id + "/add/employee/" + 2));

        if (projectRepository.findById(id).isPresent()) {
            final var loadedEntity = projectRepository.findById(id).get().getEmployeeIds();
            assertThat(loadedEntity.stream().map(ProjectEmployee::getEmployeeId)
                    .anyMatch(employeeId -> employeeId.equals(2L)), is(true));
        }

    }
}
