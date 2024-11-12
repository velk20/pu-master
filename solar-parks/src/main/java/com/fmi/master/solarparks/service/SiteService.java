package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.exception.SiteNotFoundException;
import com.fmi.master.solarparks.model.Site;
import com.fmi.master.solarparks.repository.SiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {
    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
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

    public ResponseEntity<Site> getSiteById(Long id) {
        return ResponseEntity.ok(findSiteOrThrow(id));
    }

    public ResponseEntity<Site> createSite(Site site) {
        Site save = siteRepository.save(site);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    public ResponseEntity<Site> updateSite(Long id, Site newSite) {
        Site site = findSiteOrThrow(id);
        site.setAddress(newSite.getAddress())
                .setActive(newSite.isActive())
                .setConfigCost(newSite.getConfigCost())
                .setOtherCost(newSite.getOtherCost());

        return ResponseEntity.ok(siteRepository.save(site));
    }

    public ResponseEntity<Void> deleteSite(Long id) {
        Site site = findSiteOrThrow(id);
        siteRepository.delete(site);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Site findSiteOrThrow(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new SiteNotFoundException("Site not found!"));
    }

}
