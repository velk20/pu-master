package com.fmi.master.solarparks.repository;

import com.fmi.master.solarparks.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<List<Project>> findAllByCustomerId(Long customerId);
    Optional<List<Project>> findAllByContactsId(Long contactsId);
}
