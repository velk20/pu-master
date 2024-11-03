package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.model.Sites;
import com.fmi.master.solarparks.service.SitesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sites")
public class SitesController {

    private final SitesService sitesService;

    public SitesController(SitesService sitesService) {
        this.sitesService = sitesService;
    }

    @GetMapping
    public List<Sites> getAllSites() {
        return sitesService.getAllSites();
    }

    @GetMapping("/project/{projectId}")
    public List<Sites> getSitesByProjectId(@PathVariable Long projectId) {
        return sitesService.getSitesByProjectId(projectId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sites> getSiteById(@PathVariable Long id) {
        return sitesService.getSiteById(id);
    }

    @PostMapping
    public Sites createSite(@RequestBody Sites site) {
        return sitesService.createSite(site);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sites> updateSite(@PathVariable Long id, @RequestBody Sites site) {
        return sitesService.updateSite(id, site);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        return sitesService.deleteSite(id);
    }
}
