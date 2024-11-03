package com.fmi.master.solarparks.repository;

import com.fmi.master.solarparks.model.Sites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitesRepository extends JpaRepository<Sites, Long> {
}
