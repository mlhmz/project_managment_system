package de.szut.lf8_project.project.repositories;

import de.szut.lf8_project.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> getProjectsByCustomerId(long customerId);
    List<Project> getProjectsByResponsibleEmployeeId(long employeeId);
}
