package de.szut.lf8_project.project.repositories;

import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {
    List<ProjectEmployee> getProjectEmployeesByEmployeeId(long employeeId);
    boolean existsProjectEmployeeByProjectIdAndEmployeeId(long projectId, long employeeId);
    boolean deleteProjectEmployeeByProjectIdAndEmployeeId(long projectId, long employeeId);
}