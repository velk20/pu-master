package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.exception.ProjectNotFoundException;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByCustomerId(Long customerId) {
        return projectRepository
                .findAllByCustomerId(customerId)
                .orElse(new ArrayList<>());
    }

    public ResponseEntity<Project> getProjectById(Long id) {
        return ResponseEntity.ok(findProjectOrThrow(id));
    }

    public ResponseEntity<Project> createProject(Project project) {
        Project save = projectRepository.save(project);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    public ResponseEntity<Project> updateProject(Long id, Project newProject) {
        Project project = findProjectOrThrow(id);
        project.setName(newProject.getName())
                .setActive(newProject.isActive())
                .setCost(newProject.getCost());
        return ResponseEntity.ok(projectRepository.save(project));

    }

    public ResponseEntity<Void> deleteProject(Long id) {
        Project project = findProjectOrThrow(id);
        projectRepository.delete(project);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found!"));
    }
}
