package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.dto.ProjectDTO;
import com.fmi.master.solarparks.dto.create.CreateProjectDTO;
import com.fmi.master.solarparks.exception.ContactNotFoundException;
import com.fmi.master.solarparks.exception.CustomerNotFoundException;
import com.fmi.master.solarparks.exception.ProjectNotFoundException;
import com.fmi.master.solarparks.model.Contact;
import com.fmi.master.solarparks.model.Customer;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.model.Site;
import com.fmi.master.solarparks.repository.ContactRepository;
import com.fmi.master.solarparks.repository.CustomerRepository;
import com.fmi.master.solarparks.repository.ProjectRepository;
import com.fmi.master.solarparks.repository.SiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final CustomerRepository customerRepository;
    private final SiteRepository siteRepository;
    private final ContactRepository contactRepository;
    private final ProjectRepository projectRepository;

    public ProjectService(CustomerRepository customerRepository, SiteRepository siteRepository, ContactRepository contactRepository, ProjectRepository projectRepository) {
        this.customerRepository = customerRepository;
        this.siteRepository = siteRepository;
        this.contactRepository = contactRepository;
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByCustomerId(Long customerId) {
        return projectRepository
                .findAllByCustomerId(customerId)
                .orElse(new ArrayList<>());
    }

    public Project getProjectById(Long id) {
        return findProjectOrThrow(id);
    }

    public Project createProject(CreateProjectDTO projectDTO) {
        Customer customer = findCustomerByIdOrThrow(projectDTO);

        List<Contact> contacts = findContactsByIdOrThrow(projectDTO);

        Project newProject = new Project()
                .setActive(projectDTO.getActive())
                .setName(projectDTO.getName())
                .setCost(projectDTO.getCost())
                .setCustomer(customer)
                .setContacts(contacts);

        customer.increaseNumberOfProjects();
        customerRepository.save(customer);
        return projectRepository.save(newProject);
    }


    public Project updateProject(Long id, CreateProjectDTO projectDTO) {

        Project project = findProjectOrThrow(id);
        Customer customer = findCustomerByIdOrThrow(projectDTO);
        List<Contact> contacts = findContactsByIdOrThrow(projectDTO);

        project.setName(projectDTO.getName())
                .setActive(projectDTO.getActive())
                .setCost(projectDTO.getCost())
                .setCustomer(customer)
                .setContacts(contacts);


        return projectRepository.save(project);

    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = findProjectOrThrow(id);

        List<Site> sites = siteRepository.findAllByProjectId(project.getId()).orElse(new ArrayList<>());
        sites.forEach(s -> s.setProject(null));

        siteRepository.saveAll(sites);

        projectRepository.delete(project);
    }

    private List<Contact> findContactsByIdOrThrow(CreateProjectDTO projectDTO) {
        List<Contact> contacts = new ArrayList<>();

        List<Long> contactsID = projectDTO.getContacts();
        for (Long id : contactsID) {
            Contact contact = contactRepository.findById(id)
                    .orElseThrow(() -> new ContactNotFoundException(String.format("Contact with id:%s not found", id)));

            contacts.add(contact);
        }
        return contacts;
    }

    private Customer findCustomerByIdOrThrow(CreateProjectDTO projectDTO) {
       return customerRepository.findById(projectDTO.getCustomer())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer %s not found", projectDTO.getCustomer())));
    }

    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id:%s not found!", id)));
    }

}
