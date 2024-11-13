package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.dto.create.CreateProjectDTO;
import com.fmi.master.solarparks.http.AppResponse;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.service.ProjectService;
import com.fmi.master.solarparks.util.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final DtoMapper dtoMapper;

    public ProjectController(ProjectService projectService, DtoMapper dtoMapper) {
        this.projectService = projectService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        List<Project> allProjects = projectService.getAllProjects();
        return AppResponse.success()
                .withData(dtoMapper.convertProject(allProjects))
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getProjectsByCustomerId(@PathVariable Long customerId) {
        List<Project> projectsByCustomerId = projectService.getProjectsByCustomerId(customerId);
        return AppResponse.success()
                .withData(dtoMapper.convertProject(projectsByCustomerId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        Project projectById = projectService.getProjectById(id);
        return AppResponse.success()
                .withData(dtoMapper.convertProject(projectById))
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDTO projectDTO) {
       Project newProject = projectService.createProject(projectDTO);
        return AppResponse.created()
                .withMessage("Project created successfully")
                .withData(dtoMapper.convertProject(newProject))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody CreateProjectDTO projectDTO) {
        Project projectResponseEntity = projectService.updateProject(id, projectDTO);
        return AppResponse.success()
                .withMessage("Project updated successfully")
                .withData(dtoMapper.convertProject(projectResponseEntity))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return AppResponse.deleted()
                .withMessage("Project deleted successfully")
                .build();

    }
}
