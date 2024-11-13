package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.dto.create.CreateSiteDTO;
import com.fmi.master.solarparks.http.AppResponse;
import com.fmi.master.solarparks.model.Site;
import com.fmi.master.solarparks.service.SiteService;
import com.fmi.master.solarparks.util.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sites")
public class SitesController {

    private final SiteService siteService;
    private final DtoMapper dtoMapper;

    public SitesController(SiteService siteService, DtoMapper dtoMapper) {
        this.siteService = siteService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllSites() {
        List<Site> allSites = siteService.getAllSites();
        return AppResponse.success()
                .withData(dtoMapper.convertSite(allSites))
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getSitesByProjectId(@PathVariable Long projectId) {
        List<Site> sitesByProjectId = siteService.getSitesByProjectId(projectId);
        return AppResponse.success()
                .withData(dtoMapper.convertSite(sitesByProjectId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSiteById(@PathVariable Long id) {
        Site siteById = siteService.getSiteById(id);
        return AppResponse.success()
                .withData(dtoMapper.convertSite(siteById))
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createSite(@RequestBody CreateSiteDTO siteDTO) {
        Site newSite = siteService.createSite(siteDTO);
        return AppResponse.created()
                .withData(dtoMapper.convertSite(newSite))
                .withMessage("Site created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSite(@PathVariable Long id, @RequestBody CreateSiteDTO siteDTO) {
        Site siteResponseEntity = siteService.updateSite(id, siteDTO);
        return AppResponse.success()
                .withData(dtoMapper.convertSite(siteResponseEntity))
                .withMessage("Site updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return AppResponse.deleted()
                .withMessage("Site deleted successfully")
                .build();
    }
}
