package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.model.Site;
import com.fmi.master.solarparks.service.SiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sites")
public class SitesController {

    private final SiteService siteService;

    public SitesController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    public List<Site> getAllSites() {
        return siteService.getAllSites();
    }

    @GetMapping("/project/{projectId}")
    public List<Site> getSitesByProjectId(@PathVariable Long projectId) {
        return siteService.getSitesByProjectId(projectId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable Long id) {
        return siteService.getSiteById(id);
    }

    @PostMapping
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        return siteService.createSite(site);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable Long id, @RequestBody Site site) {
        return siteService.updateSite(id, site);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        return siteService.deleteSite(id);
    }
}
