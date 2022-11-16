package de.szut.lf8_project;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import testcontainers.AbstractIntegrationTest;

import java.io.IOException;
import java.util.Map;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AuthorizedIT extends AbstractIntegrationTest {
    private static final String JWT_URL = "https://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token";

    protected String fetchJWT() throws IOException {
        String body = new RestTemplate()
                .postForEntity(JWT_URL, this.createHttpEntity(), String.class).getBody();

        Map<?, ?> map = new ObjectMapper().readValue(body, Map.class);

        return "Bearer " + map.get("access_token");
    }

    private HttpEntity<MultiValueMap<String, String>> createHttpEntity() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("username", "user");
        requestBody.add("password", "test");
        requestBody.add("client_id", "employee-management-service");
        requestBody.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(requestBody, headers);
    }
}
