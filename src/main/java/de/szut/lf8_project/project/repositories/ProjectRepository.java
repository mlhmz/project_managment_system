package de.szut.lf8_project.project.repositories;

import de.szut.lf8_project.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
