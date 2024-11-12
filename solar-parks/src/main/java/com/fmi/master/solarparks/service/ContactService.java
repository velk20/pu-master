package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.exception.ContactNotFoundException;
import com.fmi.master.solarparks.exception.ProjectNotFoundException;
import com.fmi.master.solarparks.model.Contact;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.repository.ContactRepository;
import com.fmi.master.solarparks.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ProjectRepository projectRepository;

    public ContactService(ContactRepository contactRepository, ProjectRepository projectRepository) {
        this.contactRepository = contactRepository;
        this.projectRepository = projectRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> getContactsByProjectId(Long projectId) {
        Project project = findProjectByIdOrThrow(projectId);
        return project.getContacts();
    }

    public ResponseEntity<Contact> getContactById(Long id) {
        Contact contact = findContactOrThrow(id);
        return ResponseEntity.ok(contact);
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public ResponseEntity<Contact> updateContact(Long id, Contact newContact) {
        Contact contact = findContactOrThrow(id);
        contact.setActive(newContact.isActive())
                .setEmail(newContact.getEmail())
                .setFirstName(newContact.getFirstName())
                .setLastName(newContact.getLastName())
                .setPhone(newContact.getPhone());
        return ResponseEntity.ok(contactRepository.save(contact));
    }

    public ResponseEntity<Void> deleteContact(Long id) {
        Contact contact = findContactOrThrow(id);
        contactRepository.delete(contact);
        return ResponseEntity.noContent().build();
    }

    private Project findProjectByIdOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found!"));
    }

    private Contact findContactOrThrow(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found!"));
    }
}
