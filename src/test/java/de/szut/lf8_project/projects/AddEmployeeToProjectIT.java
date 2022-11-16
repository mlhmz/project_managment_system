package de.szut.lf8_project.projects;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import testcontainers.AbstractIntegrationTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddEmployeeToProjectIT extends AbstractIntegrationTest {

    @Test
    public void addEmployeeToProject() throws Exception {
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

        final var response = this.mockMvc.perform(put("/" + id + "/add/employee/" + 2)).andExpect(status().isOk());

        final var loadedEntity = projectEmployeeRepository.existsProjectEmployeeByProjectIdAndEmployeeId(1, 2);
        assertThat(loadedEntity, is(true));
    }
}
