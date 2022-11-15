package de.szut.lf8_project.project.repositories;

import de.szut.lf8_project.project.entities.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {
    boolean existsProjectEmployeeByProjectIdAndEmployeeId(long projectId, long employeeId);
    boolean deleteProjectEmployeeByProjectIdAndEmployeeId(long projectId, long employeeId);
}