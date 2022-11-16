package de.szut.lf8_project.projects;

import org.json.JSONObject;
import org.junit.Test;
import testcontainers.AbstractIntegrationTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class AddNewProjectIT extends AbstractIntegrationTest {

    @Test
    public void createProject() throws Exception{
        String content = """
                {
                         "responsibleEmployeeId": "1",
                         "customerId": "1",
                         "comment": "This is a new project!",
                         "startDate": "2022-12-24_12-00-00",
                         "plannedEndDate": "2022-12-27_23-59-59"
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("responsibleEmployeeId", is("1")))
                .andExpect(jsonPath("customerId", is("1")))
                .andExpect(jsonPath("comment", is("This is a new project!")))
                .andExpect(jsonPath("startDate", is("2022-12-24_12-00-00")))
                .andExpect(jsonPath("plannedEndDate", is("2022-12-27_23-59-59")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        final var loadedEntity = projectRepository.findById(id);
        assertThat(loadedEntity.get().getResponsibleEmployeeId(), is(1));
        assertThat(loadedEntity.get().getCustomerId().isEqualTo("1"));
        assertThat(loadedEntity.get().getComment().isEqualTo("This is a new project!"));
        assertThat(loadedEntity.get().getStartDate().isEqualTo("2022-12-24_12-00-00"));
        assertThat(loadedEntity.get().getEndDate().isEqualTo("2022-12-27_23-59-59"));
    }
}
