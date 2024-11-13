package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.dto.create.CreateSiteDTO;
import com.fmi.master.solarparks.exception.ProjectNotFoundException;
import com.fmi.master.solarparks.exception.SiteNotFoundException;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.model.Site;
import com.fmi.master.solarparks.repository.ProjectRepository;
import com.fmi.master.solarparks.repository.SiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {
    private final ProjectRepository projectRepository;
    private final SiteRepository siteRepository;

    public SiteService(ProjectRepository projectRepository, SiteRepository siteRepository) {
        this.projectRepository = projectRepository;
        this.siteRepository = siteRepository;
    }

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public List<Site> getSitesByProjectId(Long projectId) {
        return siteRepository
                .findAllByProjectId(projectId)
                .orElse(new ArrayList<>());
    }

    public Site getSiteById(Long id) {
        return findSiteOrThrow(id);
    }

    public Site createSite(CreateSiteDTO siteDTO) {
        Project project = findProjectOrThrow(siteDTO.getProject());

        Site site = new Site()
                .setActive(siteDTO.getActive())
                .setAddress(siteDTO.getAddress())
                .setName(siteDTO.getName())
                .setProject(project)
                .setConfigCost(siteDTO.getConfigCost())
                .setOtherCost(siteDTO.getOtherCost());

        return siteRepository.save(site);
    }

    public Site updateSite(Long id, CreateSiteDTO siteDTO) {
        Site site = findSiteOrThrow(id);
        Project project = findProjectOrThrow(siteDTO.getProject());

        site.setAddress(siteDTO.getAddress())
                .setActive(siteDTO.getActive())
                .setConfigCost(siteDTO.getConfigCost())
                .setOtherCost(siteDTO.getOtherCost())
                .setName(siteDTO.getName())
                .setProject(project);

        return siteRepository.save(site);
    }

    public void deleteSite(Long id) {
        Site site = findSiteOrThrow(id);
        siteRepository.delete(site);
    }

    private Site findSiteOrThrow(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new SiteNotFoundException(String.format("Site with id:%s not found!", id)));
    }

    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id:%s not found!", id)));
    }

}
