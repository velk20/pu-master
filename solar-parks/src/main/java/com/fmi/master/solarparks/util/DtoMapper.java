package com.fmi.master.solarparks.util;

import com.fmi.master.solarparks.dto.ContactDTO;
import com.fmi.master.solarparks.dto.CustomerDTO;
import com.fmi.master.solarparks.dto.ProjectDTO;
import com.fmi.master.solarparks.dto.SiteDTO;
import com.fmi.master.solarparks.model.Contact;
import com.fmi.master.solarparks.model.Customer;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.model.Site;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {
    public  List<ContactDTO> convertContact(List<Contact> contacts) {
        return contacts.stream().map(this::convertContact).toList();
    }
    public  ContactDTO convertContact(Contact contact) {
        return new ContactDTO()
                .setId(contact.getId())
                .setFirstName(contact.getFirstName())
                .setLastName(contact.getLastName())
                .setEmail(contact.getEmail())
                .setPhone(contact.getPhone())
                .setActive(intToBoolean(contact.isActive()));

    }

    public  List<ProjectDTO> convertProject(List<Project> projects) {
        return projects.stream().map(this::convertProject).toList();
    }

    public  ProjectDTO convertProject(Project project) {
        return new ProjectDTO()
                .setId(project.getId())
                .setName(project.getName())
                .setCost(project.getCost())
                .setActive(intToBoolean(project.isActive()))
                .setContacts(project.getContacts().stream().map(this::convertContact).toList())
                .setCustomer(convertCustomer(project.getCustomer()));
    }

    private ProjectDTO convertProjectShallow(Project project) {
        return new ProjectDTO()
                .setId(project.getId())
                .setName(project.getName())
                .setCost(project.getCost())
                .setActive(intToBoolean(project.isActive()))
                .setCustomer(convertCustomer(project.getCustomer()));
    }

    public  List<SiteDTO> convertSite(List<Site> sites) {
        return sites.stream().map(this::convertSite).toList();
    }

    public  SiteDTO convertSite(Site site) {
        return new SiteDTO()
                .setId(site.getId())
                .setName(site.getName())
                .setAddress(site.getAddress())
                .setConfigCost(site.getConfigCost())
                .setOtherCost(site.getOtherCost())
                .setProject(convertProject(site.getProject()))
                .setActive(intToBoolean(site.isActive()));
    }

    public  List<CustomerDTO> convertCustomer(List<Customer> customers) {
        return customers.stream().map(this::convertCustomer).toList();
    }

    public  CustomerDTO convertCustomer(Customer customer) {
        return new CustomerDTO()
                .setId(customer.getId())
                .setName(customer.getName())
                .setActive(intToBoolean(customer.isActive()))
                .setNumberOfProjects(customer.getNumberOfProjects());
    }

    private  boolean intToBoolean(int value) {
        return value != 0;
    }
}
