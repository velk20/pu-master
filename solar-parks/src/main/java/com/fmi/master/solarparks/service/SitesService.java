package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.model.Sites;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitesService {
    public List<Sites> getAllSites() {
        return null;
    }

    public List<Sites> getSitesByProjectId(Long projectId) {
        return null;
    }

    public ResponseEntity<Sites> getSiteById(Long id) {
        return null;
    }

    public Sites createSite(Sites site) {
        return null;
    }

    public ResponseEntity<Sites> updateSite(Long id, Sites site) {
        return null;
    }

    public ResponseEntity<Void> deleteSite(Long id) {
        return null;
    }
}
