package com.fmi.master.solarparks.repository;

import com.fmi.master.solarparks.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
}
