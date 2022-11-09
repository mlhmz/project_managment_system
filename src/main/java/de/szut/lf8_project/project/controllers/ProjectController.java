package de.szut.lf8_project.project.controllers;

import de.szut.lf8_project.employee.EmployeeService;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.mapping.MappingService;
import de.szut.lf8_project.project.services.ProjectService;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.entities.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final MappingService mappingService;
    private final EmployeeService employeeService;

    public ProjectController(ProjectService projectService, MappingService mappingService,
                             EmployeeService employeeService) {
        this.projectService = projectService;
        this.mappingService = mappingService;
        this.employeeService = employeeService;
    }

    /**
     * Erstellt ein Projekt mit einer Post-Request
     * <p>
     * TODO: Sobald das GetObjectDto erstellt wurde sollte dies durch das Project als ResponseEntity ersetzt werden
     */
    @Operation(summary = "creates a new project with its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "If the customer or the responsible " +
                    "Employee couldn't be found")}
    )
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody @Valid final CreateProjectDto dto,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        // TODO: Mit GetArticleDTO als Hateoas einbinden
        //  - Existenz prüfen mit einer isEmployeeExisting() Methode
        //  - Wenn existiert dann HATEOAS quasi einbinden
        long responsibleEmployeeId = dto.getResponsibleEmployeeId();
        if (employeeService.isEmployeeExisting(responsibleEmployeeId, token)) {
            // TODO: Nötigen HATEOAS setzen
        } else {
            throw new ResourceNotFoundException(String.format("The employee with the id %d couldn't be found.",
                    responsibleEmployeeId));
        }


        return new ResponseEntity<>(projectService.createProject(
                mappingService.mapCreateProjectDtoToProject(dto)
        ), HttpStatus.CREATED);
    }

    /**
     * Löscht einen Mitarbeiter aus einem Project mittels Delete-Request
     * <p>
     * TODO: Sobald das GetObjectDto erstellt wurde sollte dies durch das Project als ResponseEntity ersetzt werden
     */
    @Operation(summary = "creates a new project with its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "If employee is not involved in project",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "If the project or the employee could't be found")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteEmployeeFromProject(@PathVariable Long id, @RequestBody long employeeId,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        if (employeeService.isEmployeeExisting(employeeId, token)) {
            // TODO: Nötigen HATEOAS setzen
            if(this.projectService.isEmployeeInvolvedInProject(id,employeeId)){
                if(this.projectService.deleteEmployeeFromProject(id,employeeId)){
                    return new ResponseEntity<>(HttpStatus.OK);
                }else{
                    throw new RuntimeException(String.format("Something went wrong in the deletion process for " +
                                    "project %d and employee %d", id, employeeId));
                }
            }else{
                throw new ResourceNotFoundException(String.format("The employee %d couldn't be found in project %d.",
                        employeeId, id));
            }
        } else {
            throw new ResourceNotFoundException(String.format("The employee with the id %d couldn't be found.",
                    employeeId));
        }
    }
}
