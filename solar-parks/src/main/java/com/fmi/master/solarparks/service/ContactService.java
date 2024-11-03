package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    public List<Contact> getAllContacts() {
        return null;
    }

    public List<Contact> getContactsByProjectId(Long projectId) {
        return null;
    }

    public ResponseEntity<Contact> getContactById(Long id) {
        return null;
    }

    public Contact createContact(Contact contact) {
        return null;
    }

    public ResponseEntity<Contact> updateContact(Long id, Contact contact) {
        return null;
    }

    public ResponseEntity<Void> deleteContact(Long id) {
        return null;
    }
}
