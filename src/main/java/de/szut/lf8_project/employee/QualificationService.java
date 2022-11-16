package de.szut.lf8_project.employee;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QualificationService {
    private static final String EMPLOYEE_URL = "https://employee.szut.dev/qualifications";
    private final RestTemplate template;

    public QualificationService() {
        template = new RestTemplate();
    }

    public boolean isQualificationExisting(String qualification, String bearerToken) {
        GetQualificationDto qualificationDto = new GetQualificationDto(qualification);
        List<GetQualificationDto> allQualifications = getAllQualifications(bearerToken);
        return allQualifications.contains(qualificationDto);
    }

    public List<GetQualificationDto> getAllQualifications(String bearerToken) {
        RequestEntity<?> request = RequestEntity.get(EMPLOYEE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken).build();

        return template.exchange(request, new ParameterizedTypeReference<List<GetQualificationDto>>() {}).getBody();
    }
}
