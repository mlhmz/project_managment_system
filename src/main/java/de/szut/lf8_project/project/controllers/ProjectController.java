package de.szut.lf8_project.project.controllers;

import de.szut.lf8_project.employee.EmployeeService;
import de.szut.lf8_project.employee.GetEmployeeReferencesDto;
import de.szut.lf8_project.exceptionHandling.ErrorDetails;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.mapping.MappingService;
import de.szut.lf8_project.project.dto.ChangeProjectDto;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.entities.Project;
import de.szut.lf8_project.project.entities.ProjectEmployee;
import de.szut.lf8_project.project.services.ProjectEmployeeService;
import de.szut.lf8_project.project.services.ProjectService;
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
import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectEmployeeService projectEmployeeService;
    private final MappingService mappingService;
    private final EmployeeService employeeService;

    public ProjectController(ProjectService projectService, ProjectEmployeeService projectEmployeeService,
                             MappingService mappingService,
                             EmployeeService employeeService) {
        this.projectService = projectService;
        this.projectEmployeeService = projectEmployeeService;
        this.mappingService = mappingService;
        this.employeeService = employeeService;
    }

    /**
     * Erstellt ein Projekt mit einer Post-Request
     */
    @Operation(summary = "Creates a new project with its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created project",
                    content = {@Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON posted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Customer or the responsible Employee couldn't be found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}
            )}
    )
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/hal+json"
    )
    public ResponseEntity<GetProjectDto> createProject(@RequestBody @Valid final CreateProjectDto dto,
                                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        Project project = mappingService.mapCreateProjectDtoToProject(dto);

        long responsibleEmployeeId = dto.getResponsibleEmployeeId();
        if (!employeeService.isEmployeeExisting(responsibleEmployeeId, token)) {
            throw new ResourceNotFoundException(String.format("The employee with the id %d couldn't be found.",
                    responsibleEmployeeId));
        }

        Project createdProject = projectService.saveProject(project);
        return new ResponseEntity<>(mappingService.mapProjectToGetProjectDto(createdProject), HttpStatus.CREATED);
    }

    @Operation(summary = "Gets employee references from a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found project",
                    content = {@Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = GetEmployeeReferencesDto.class))}),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Project couldn't be found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))})}
    )
    @RequestMapping(
            value = "/{id}/employees",
            method = RequestMethod.GET,
            produces = "application/hal+json"
    )
    public ResponseEntity<GetEmployeeReferencesDto> getEmployeesFromProject(@PathVariable long id) {
        return new ResponseEntity<>(
                mappingService.mapProjectEmployeesToGetEmployeeReferencesDto(projectService.readAllEmployeesFromProject(id)),
                HttpStatus.OK
        );
    }


    @Operation(summary = "Gets projects of a responsible employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found project",
                    content = {@Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = GetEmployeeReferencesDto.class))}),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Responsible Employee couldn't be found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))})
    }
    )
    @RequestMapping(
            value = "/responsible/{employeeId}",
            method = RequestMethod.GET,
            produces = "application/hal+json"
    )
    public ResponseEntity<List<GetProjectDto>> getProjectsByResponsibleEmployee(@PathVariable long employeeId) {
        return new ResponseEntity<>(
                mappingService.mapProjectsToGetProjectList(projectService.readProjectsByResponsibleEmployeeId(employeeId)),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Gets projects of a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found project",
                    content = {@Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = GetEmployeeReferencesDto.class))}),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Customer couldn't be found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))})}
    )
    @RequestMapping(
            value = "/customer/{customerId}",
            method = RequestMethod.GET,
            produces = "application/hal+json"
    )
    public ResponseEntity<List<GetProjectDto>> getProjectsByCustomer(@PathVariable long customerId) {
        List<Project> projectList = this.projectService.readProjectsByCustomerId(customerId);
        return new ResponseEntity<>(mappingService.mapProjectsToGetProjectList(projectList), HttpStatus.OK);
    }

    @Operation(summary = "Gets all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found project",
                    content = {@Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = GetEmployeeReferencesDto.class))}),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))})
    }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/hal+json"
    )
    public ResponseEntity<List<GetProjectDto>> getProjects() {
        List<Project> projectList = this.projectService.readAllProjects();
        return new ResponseEntity<>(mappingService.mapProjectsToGetProjectList(projectList), HttpStatus.OK);
    }

    @Operation(summary = "Gets a project by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found project",
                    content = {@Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = GetEmployeeReferencesDto.class))}),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Project couldn't be found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))})
    }
    )
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/hal+json"
    )
    public ResponseEntity<GetProjectDto> getProjectById(@PathVariable long id) {
        Project project = projectService.readProjectById(id);
        return new ResponseEntity<>(mappingService.mapProjectToGetProjectDto(project), HttpStatus.OK);
    }

    @Operation(summary = "Updates a project with its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChangeProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request doesn't contain valid bearer token",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project or employee couldn't be found")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody @Valid final ChangeProjectDto dto,
                                                 @PathVariable Long id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long responsibleEmployeeId = dto.getResponsibleEmployeeId();

        if (responsibleEmployeeId == null || !employeeService.isEmployeeExisting(responsibleEmployeeId, token)) {
            throw new ResourceNotFoundException(String.format("The employee with the id %d couldn't be found.",
                    responsibleEmployeeId));
        }

        Project project = mappingService.mapUpdateProjectDtoIntoProject(dto, projectService.readProjectById(id));

        return new ResponseEntity<>(projectService.saveProject(project), HttpStatus.OK);
    }

    @Operation(summary = "Deletes a project by its unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deleted project"),
            @ApiResponse(responseCode = "401", description = "Project doesn't contain valid bearer token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Project couldn't be found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))})}
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteProjectById(@PathVariable Long id) {
        if (projectService.isProjectExisting(id)) {
            projectService.deleteProject(id);
        } else {
            throw new ResourceNotFoundException("The Project with the id %d couldn't be found ");
        }
    }

    @PutMapping("/{id}/add/employee/{employeeId)")
    public ResponseEntity<GetProjectDto> addProjectEmployeeToProject(@RequestParam Long id, @RequestParam Long employeeId,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        if(!employeeService.isEmployeeExisting(employeeId, token)) {
            throw new ResourceNotFoundException(String.format("The Employee with the Id %d couldn't be found", employeeId));
        }

        Project project = projectService.readProjectById(id);

        if (!projectEmployeeService.isEmployeeAvailableInTimespan(employeeId, project.getStartDate(), project.getEndDate())) {
            // TODO: 400?
            throw new RuntimeException("Employee not available in Timeslot");
        }

        ProjectEmployee projectEmployee = mappingService.buildProjectEmployee(id);

        GetProjectDto dto = mappingService.mapProjectToGetProjectDto(
                projectService.addProjectEmployeeToProject(project, projectEmployee)
        );

        return new ResponseEntity<>(dto, HttpStatus.OK);
     }
  
    @Operation(summary = "Removes an Employee from a Project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removed Employee",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "If employee is not involved in project",
                    content = @Content)}
    )
    @PutMapping("/{id}/remove/employee/{employeeId}")
    public ResponseEntity<GetProjectDto> deleteEmployeeFromProject(@PathVariable Long id, @PathVariable Long employeeId) {
        if (this.projectEmployeeService.isEmployeeInvolvedInProject(id, employeeId) &&
                this.projectEmployeeService.removeEmployeeFromProject(id, employeeId)) {
            return new ResponseEntity<>(
                    mappingService.mapProjectToGetProjectDto(
                            this.projectService.readProjectById(id)
                    ), HttpStatus.OK
            );
        } else {
            // TODO: Change to 400 after ExceptionHandling
            throw new ResourceNotFoundException(String.format("The employee %d isn't involved in Project %d.",
                    employeeId, id));
        }
    }
}
