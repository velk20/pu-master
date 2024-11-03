package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    public List<Project> getAllProjects() {
        return null;
    }

    public List<Project> getProjectsByCustomerId(Long customerId) {
        return null;
    }

    public ResponseEntity<Project> getProjectById(Long id) {
        return null;
    }

    public Project createProject(Project project) {
        return null;
    }

    public ResponseEntity<Project> updateProject(Long id, Project project) {
        return null;
    }

    public ResponseEntity<Void> deleteProject(Long id) {
        return null;
    }
}
