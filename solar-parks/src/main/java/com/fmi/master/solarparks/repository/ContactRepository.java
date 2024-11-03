package com.fmi.master.solarparks.repository;

import com.fmi.master.solarparks.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
