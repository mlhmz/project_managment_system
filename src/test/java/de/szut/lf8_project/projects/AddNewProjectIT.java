package de.szut.lf8_project.projects;

import de.szut.lf8_project.AuthorizedIT;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddNewProjectIT extends AuthorizedIT {

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

        final var contentAsString = this.mockMvc.perform(post("/project")
                .content(content).contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, fetchJWT()))
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

        assertThat(loadedEntity.isPresent(), is(true));
        assertThat(loadedEntity.get(), hasProperty("id", equalTo(1)));
        assertThat(loadedEntity.get().getCustomerId(), equalTo("1"));
        assertThat(loadedEntity.get().getComment(), equalTo("This is a new project!"));
        assertThat(loadedEntity.get().getStartDate(), equalTo("2022-12-24_12-00-00"));
        assertThat(loadedEntity.get().getEndDate(), equalTo("2022-12-27_23-59-59"));
    }
}
