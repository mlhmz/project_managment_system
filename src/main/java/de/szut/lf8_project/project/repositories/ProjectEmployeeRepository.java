package de.szut.lf8_project.project.repositories;

import de.szut.lf8_project.project.entities.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {
    Optional<ProjectEmployee> findProjectEmployeeByProjectIdAndEmployeeId(long projectId, long employeeId);
    boolean deleteProjectEmployeeByProjectIdAndEmployeeId(long projectId, long employeeId);
}