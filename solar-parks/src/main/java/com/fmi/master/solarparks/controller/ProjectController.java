package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/customer/{customerId}")
    public List<Project> getProjectsByCustomerId(@PathVariable Long customerId) {
        return projectService.getProjectsByCustomerId(customerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }
}
