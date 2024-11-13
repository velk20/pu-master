package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.dto.create.CreateContactDTO;
import com.fmi.master.solarparks.http.AppResponse;
import com.fmi.master.solarparks.model.Contact;
import com.fmi.master.solarparks.service.ContactService;
import com.fmi.master.solarparks.util.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final DtoMapper dtoMapper;

    public ContactController(ContactService contactService, DtoMapper dtoMapper) {
        this.contactService = contactService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllContacts() {
        List<Contact> allContacts = contactService.getAllContacts();
        return AppResponse.success()
                .withData(dtoMapper.convertContact(allContacts))
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getContactsByProjectId(@PathVariable Long projectId) {
        List<Contact> contactsByProjectId = contactService.getContactsByProjectId(projectId);
        return AppResponse.success()
                .withData(dtoMapper.convertContact(contactsByProjectId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        return AppResponse.success()
                .withData(dtoMapper.convertContact(contact))
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody CreateContactDTO contact) {
        Contact newContact = contactService.createContact(contact);
        return AppResponse.created()
                .withData(dtoMapper.convertContact(newContact))
                .withMessage("Contact created successfully")
                .build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody CreateContactDTO contact) {
        Contact contactResponseEntity = contactService.updateContact(id, contact);
        return AppResponse.success()
                .withData(dtoMapper.convertContact(contactResponseEntity))
                .withMessage("Contact updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return AppResponse.deleted()
                .withMessage("Contact successfully deleted")
                .build();

    }
}
