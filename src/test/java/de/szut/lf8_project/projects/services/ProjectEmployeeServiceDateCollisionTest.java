package de.szut.lf8_project.projects.services;

import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import de.szut.lf8_project.project.services.ProjectEmployeeService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit-Test to check if the Date Colliding Service Check works
 */
class ProjectEmployeeServiceDateCollisionTest {
    @ParameterizedTest
    @CsvSource({"2022/11/17,2022/11/18",
            "2022/11/14,2022/11/16",
            "2022/11/18,2022/11/20",
            "2022/10/1,2022/11/30",
    })
    void isProjectEmployeeDateCollidingTrue(String startDateString, String endDateString) {
        ProjectEmployeeService service = new ProjectEmployeeService(null);

        assertTrue(service.isProjectEmployeeDateColliding(
                convertStringDate(startDateString),
                convertStringDate(endDateString),
                buildProjectEmployee()
                )
        );

    }

    @ParameterizedTest
    @CsvSource({"2022/11/25,2022/11/27",
            "2022/11/5,2022/11/14",
    })
    void isProjectEmployeeDateCollidingFalse(String startDateString, String endDateString) {
        ProjectEmployeeService service = new ProjectEmployeeService(null);

        assertFalse(service.isProjectEmployeeDateColliding(
                convertStringDate(startDateString),
                convertStringDate(endDateString),
                buildProjectEmployee()));
    }

    private static ProjectEmployee buildProjectEmployee() {
        ProjectEmployee employee = new ProjectEmployee();

        Project project = new Project();
        project.setStartDate(getLocalDateTime(2022, 11, 15));
        project.setPlannedEndDate(getLocalDateTime(2022, 11, 19));

        employee.setProject(project);
        return employee;
    }

    private static LocalDateTime convertStringDate(String dateString) {
        String[] dateSplit = dateString.split("/");
        return getLocalDateTime(Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
    }

    private static LocalDateTime getLocalDateTime(int year, int month, int dayOfMonth) {
        return LocalDateTime.of(year, month, dayOfMonth, 14, 0);
    }
}