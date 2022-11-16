package de.szut.lf8_project.projects;

import de.szut.lf8_project.AuthorizedIT;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DeleteProjectIT extends AuthorizedIT {
    @Test
    public void deleteProjectIT() throws Exception {
        String content_project = """
                {
                         "responsibleEmployeeId": "9",
                         "customerId": "1",
                         "comment": "This is a new project!",
                         "startDate": "2022-12-24_12-00-00",
                         "plannedEndDate": "2022-12-27_23-59-59"
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("/project")
                        .content(content_project).contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, fetchJWT()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        this.mockMvc.perform(delete("/project/"+id));
        final var loadedEntity = projectRepository.findById(id).isPresent();
        assertThat(loadedEntity, is(false));
    }
}
