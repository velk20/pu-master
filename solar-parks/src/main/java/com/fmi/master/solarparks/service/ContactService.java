package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.dto.create.CreateContactDTO;
import com.fmi.master.solarparks.exception.ContactNotFoundException;
import com.fmi.master.solarparks.exception.ProjectNotFoundException;
import com.fmi.master.solarparks.model.Contact;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.repository.ContactRepository;
import com.fmi.master.solarparks.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Contact getContactById(Long id) {
        return findContactOrThrow(id);
    }

    @Transactional
    public Contact createContact(CreateContactDTO contact) {
        List<Project> projects = contact.getProjects().stream().map(this::findProjectByIdOrThrow).toList();

        Contact newContact = new Contact()
                .setActive(contact.isActive() ? 1 : 0)
                .setEmail(contact.getEmail())
                .setPhone(contact.getPhone())
                .setFirstName(contact.getFirstName())
                .setLastName(contact.getLastName());
        Contact save = contactRepository.save(newContact);
        projects.forEach(project -> {
            project.addContact(save);});

        projectRepository.saveAll(projects);
        return save;
    }

    @Transactional
    public Contact updateContact(Long id, CreateContactDTO newContact) {
        Contact contact = findContactOrThrow(id);
        List<Contact> newContacts = new ArrayList<>();

        List<Project> projects = newContact.getProjects().stream()
                .map(this::findProjectByIdOrThrow)
                .toList();

        contact.setActive(newContact.isActive() ? 1 : 0)
                .setEmail(newContact.getEmail())
                .setFirstName(newContact.getFirstName())
                .setLastName(newContact.getLastName())
                .setPhone(newContact.getPhone());

        Contact save = contactRepository.save(contact);
        newContacts.add(save);
        projects.forEach(project -> {project.setContacts(newContacts);});

        projectRepository.saveAll(projects);
        return save;
    }

    @Transactional
    public void deleteContact(Long id) {
        Contact contact = findContactOrThrow(id);
        List<Project> projects = projectRepository.findAllByContactsId(id).orElse(new ArrayList<>());
        projects.forEach(project -> {
            project.removeContact(contact);
        });
        projectRepository.saveAll(projects);
        contactRepository.delete(contact);
    }

    private Project findProjectByIdOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id:%s not found!", projectId)));
    }

    private Contact findContactOrThrow(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(String.format("Contact with id:%s not found!", id)));
    }
}
