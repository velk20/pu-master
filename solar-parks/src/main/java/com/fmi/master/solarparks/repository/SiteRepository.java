package com.fmi.master.solarparks.repository;

import com.fmi.master.solarparks.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<List<Site>> findAllByProjectId(Long projectId);
}
