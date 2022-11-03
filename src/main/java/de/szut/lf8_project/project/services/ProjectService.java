package de.szut.lf8_project.project.services;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.controllers.ProjectEmployee;
import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project createProject(Project project) {
        return this.repository.save(project);
    }

    /**
     * Holt alle Projekte
     *
     * @return alle Projekte die in der Datenbank existieren
     */
    public List<Project> readAllProjects() {
        return this.repository.findAll();
    }

    /**
     * Holt ein Projekt mit einer bestimmten id
     *
     * @param projectId die eindeutige Id des Projektes
     * @return das gesuchte Projekt
     * @throws ResourceNotFoundException Wenn gesuchtes Projekt nicht existiert
     */
    public Project readProjectById(long projectId) {
        Optional<Project> project = this.repository.findById(projectId);

        if (project.isEmpty()) {
            throw new ResourceNotFoundException(String.format("The project with the id '%d' was not found.", projectId));
        }

        return project.get();
    }

    /**
     * Holt alle Projekte mit der Id eines Kunden
     *
     * @param customerId Id des Kunden
     * @return Eine Liste mit Projekten, falls Kunde nicht eingebunden ist leer
     */
    public List<Project> readProjectsByCustomerId(long customerId) {
        return this.repository.getProjectsByCustomerId(customerId);
    }

    /**
     * Holt alle Projekte mit einem verantwortlichen Mitarbeiter
     *
     * @param employeeId Der verantwortliche Mitarbeiter
     * @return Eine Liste mit Projekten in denen der Mitarbeiter eingebunden ist, falls Mitarbeiter in keinem Projekt
     *  eingebunden ist null.
     */
    public List<Project> readProjectsByResponsibleEmployeeId(long employeeId) {
        return this.repository.getProjectsByResponsibleEmployeeId(employeeId);
    }

    /**
     * Holt alle EmployeeIds von einem Projekt
     * @param projectId Die ProjektId dessen Employees geholt werden soll
     * @return Gibt Liste mit Ids von Mitarbeitern zurück
     */
    public List<Long> getAllEmployeesIdsFromProject(long projectId) {
        Project project = readProjectById(projectId);

        return project.getEmployeeIds().stream()
                .map(ProjectEmployee::getEmployeeId)
                .collect(Collectors.toList());
    }


}
